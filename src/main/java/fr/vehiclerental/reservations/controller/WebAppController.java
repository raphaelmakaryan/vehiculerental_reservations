package fr.vehiclerental.reservations.controller;

import fr.vehiclerental.reservations.entity.*;
import fr.vehiclerental.reservations.exception.BadRequestException;
import fr.vehiclerental.reservations.exception.ReservationNotFindException;
import fr.vehiclerental.reservations.service.ReservationsDao;
import fr.vehiclerental.reservations.service.ReservationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class WebAppController {
    private final ReservationsService reservationsService;
    private final ReservationsDao reservationsDao;

    public WebAppController(ReservationsService reservationsService, ReservationsDao reservationsDao) {
        this.reservationsService = reservationsService;
        this.reservationsDao = reservationsDao;
    }


    @Operation(summary = "Page d'accueil")
    @RequestMapping("/")
    public String index() {
        return "Hello World";
    }


    @Operation(summary = "Voir toute les reservations de la base de données ", description = "Requête pour la récupération de toute les reservations de la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Saisie invalide\"}")))})
    @GetMapping("/reservations")
    public List<ReservationsDTO> reservations() {
        return reservationsDao.findAll().stream().map(c -> new ReservationsDTO(c.getId(), c.getIdClient(), c.getIdVehicule(), c.getStartReservation(), c.getEndReservation(), c.getEstimatedKm(), c.getPriceReservation())).collect(Collectors.toList());
    }


    @Operation(summary = "Voir une reservation spécifique de la base de données", description = "Requête pour la récupération d'une reservation de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Saisie invalide\"}")))})
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.GET)
    public List<ReservationsDTO> getReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int id) {
        try {
            return reservationsDao.findById(id).stream().map(c -> new ReservationsDTO(c.getId(), c.getIdClient(), c.getIdVehicule(), c.getStartReservation(), c.getEndReservation(), c.getEstimatedKm(), c.getPriceReservation())).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }


    @Operation(summary = "Crée une nouvelle reservation dans la base de données", description = "Requête pour crée/ajouter une reservation dans la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été ajoutée !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "    \"success\": false,\n" + "    \"message\": \"Votre reservation n'a pas été ajoutée !\"\n" + "}")}))})
    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addReservations(@Validated @RequestBody RequiredReservation informations) {
        try {
            Map<String, Object> response = new HashMap<>();
            List<ClientDTO> clientVerification = reservationsService.requestClient(informations.getIdClient());
            if (!clientVerification.isEmpty()) {
                ClientDTO client = clientVerification.get(0);
                if (reservationsService.canReserve(client.getBirthday(), client.getNumberLicense())) {
                    if (reservationsService.clientHaveAReservation(client.getId(), reservationsDao)) {
                        List<VehicleDTO> vehiculeVerification = reservationsService.requestVehicle(informations.getIdVehicule());
                        if (!vehiculeVerification.isEmpty()) {
                            VehicleDTO vehicle = vehiculeVerification.get(0);
                            if (reservationsService.requestYearClient(client.getBirthday(), vehicle.getHorsePower())) {
                                if (reservationsService.verificationVehiculeReservation(vehicle, informations, reservationsDao)) {
                                    int priceFinal = reservationsService.calculePriceFinal(vehicle, informations);
                                    if (priceFinal != 0) {
                                        reservationsService.createReservation(reservationsDao, client, vehicle, informations, priceFinal);
                                        response.put("success", true);
                                        response.put("message", "Votre reservations a été ajoutée !");
                                    } else {
                                        response.put("success", false);
                                        response.put("message", "La reservations ne peux pas etre ajouté !");
                                    }
                                } else {
                                    response.put("success", false);
                                    response.put("message", "Le vehicule n'est pas disponible car il est deja reserver !");
                                }
                            } else {
                                response.put("success", false);
                                response.put("message", "Le vehicule défini dans la requete n'est pas disponible selon l'age du client et les cheveaux du vehicule !");
                            }
                        } else {
                            response.put("success", false);
                            response.put("message", "Le vehicule a l'id : " + informations.getIdVehicule() + " n'existe pas !");
                        }
                    } else {
                        response.put("success", false);
                        response.put("message", "Le client a deja reservé un vehicule !");
                    }
                } else {
                    response.put("success", false);
                    response.put("message", "Le client n'a pas l'age lagale ou de permis valable !");
                }
            } else {
                response.put("success", false);
                response.put("message", "Le client a l'id : " + informations.getIdClient() + " n'existe pas !");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Votre reservations n'a pas été ajoutée !");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Operation(summary = "Mettre à jour une reservation dans la base de données", description = "Requête pour mettre a jour une reservation dans la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été modifié !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1\",\n" + "  \"status\": 404\n" + "}")

    }))})
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Map<String, Object>> editReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int idUSer) {
        try {
            List<Reservations> reservations = reservationsDao.findById(idUSer);
            if (reservations == null || reservations.isEmpty()) {
                throw new ReservationNotFindException(idUSer);
            } else {
                //reservations.get(0).set("Raphael");
                //clientDao.save(client.get(0));
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Votre reservation a été modifié !");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    @Operation(summary = "Supprimer une reservation de la base de données", description = "Requête pour supprimer une reservations de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été supprimé !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1 \",\n" + "  \"status\": 404\n" + "}")

    }))})
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Map<String, Object>> deleteReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int idUSer) {
        List<Reservations> reservations = reservationsDao.findById(idUSer);
        if (reservations == null || reservations.isEmpty()) {
            throw new ReservationNotFindException(idUSer);
        } else {
            //lientDao.delete(client.get(0));
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Votre reservations a été supprimé !");
            return ResponseEntity.ok(response);
        }
    }
}

