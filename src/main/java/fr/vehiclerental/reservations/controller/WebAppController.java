package fr.vehiclerental.reservations.controller;

import fr.vehiclerental.reservations.entity.*;
import fr.vehiclerental.reservations.exception.*;
import fr.vehiclerental.reservations.service.ReservationsDao;
import fr.vehiclerental.reservations.service.ReservationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
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

    @Operation(summary = "Voir toute les reservations de la base de données ", description = "Requête pour la récupération de toute les reservations de la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class)))})
    @GetMapping("/reservations")
    public List<ReservationsDTO> reservations() {
        return reservationsDao.findAll().stream().map(c -> new ReservationsDTO(c.getId(), c.getIdClient(), c.getIdVehicule(), c.getStartReservation(), c.getEndReservation(), c.getEstimatedKm(), c.getPriceReservation())).collect(Collectors.toList());
    }


    @Operation(summary = "Voir une reservation spécifique de la base de données", description = "Requête pour la récupération d'une reservation de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientNotFindException.class)))})
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.GET)
    public List<ReservationsDTO> getReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int id) {
        try {
            return reservationsDao.findById(id).stream().map(c -> new ReservationsDTO(c.getId(), c.getIdClient(), c.getIdVehicule(), c.getStartReservation(), c.getEndReservation(), c.getEstimatedKm(), c.getPriceReservation())).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new ClientNotFindException(id);
        }
    }


    @Operation(summary = "Crée une nouvelle reservation dans la base de données", description = "Requête pour crée/ajouter une reservation dans la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationAdd.class))), @ApiResponse(responseCode = "405", description = "Erreur métier",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Client introuvable",
                                    value = """
                                            {
                                              "timestamp": "2025-11-06T15:00:00",
                                              "status": 404,
                                              "error": "Client introuvable",
                                              "message": "Client not found with ID : 1"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Client déjà réservé",
                                    value = """
                                            {
                                              "timestamp": "2025-11-06T15:00:00",
                                              "status": 404,
                                              "error": "Client déjà réservé",
                                              "message": "Ce client dispose déjà d'une réservation en cours"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Véhicule déjà réservé",
                                    value = """
                                            {
                                              "timestamp": "2025-11-06T15:00:00",
                                              "status": 404,
                                              "error": "Véhicule déjà réservé",
                                              "message": "Le véhicule 12 est déjà réservé pour cette période"
                                            }
                                            """
                            )
                    }
            )
    )})
    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addReservations(@Validated @RequestBody RequiredReservation informations) {
        try {
            List<ClientDTO> clientVerification = reservationsService.requestClient(informations.getIdClient());
            if (!clientVerification.isEmpty()) {
                ClientDTO client = clientVerification.getFirst();
                if (reservationsService.canReserve(client.getBirthday(), client.getNumberLicense())) {
                    if (reservationsService.clientHaveAReservation(client.getId(), reservationsDao)) {
                        List<VehicleDTO> vehiculeVerification = reservationsService.requestVehicle(informations.getIdVehicule());
                        if (!vehiculeVerification.isEmpty()) {
                            VehicleDTO vehicle = vehiculeVerification.getFirst();
                            if (reservationsService.requestYearClient(client.getBirthday(), vehicle.getHorsePower())) {
                                if (reservationsService.verificationVehiculeReservation(vehicle, informations, reservationsDao)) {
                                    int priceFinal = reservationsService.calculePriceFinal(vehicle, informations);
                                    if (priceFinal != 0) {
                                        Map<String, Object> response = new HashMap<>();
                                        reservationsService.createReservation(reservationsDao, client, vehicle, informations, priceFinal);
                                        response.put("success", true);
                                        response.put("message", "Votre reservation a été ajoutée !");
                                        return ResponseEntity.ok(response);
                                    } else {
                                        throw new VehicleTypeKnowName();
                                    }
                                } else {
                                    throw new VehiculeAlreadyReservation();
                                }
                            } else {
                                throw new ClientAgeHorsepower();
                            }
                        } else {
                            throw new VehiculeNotFInd(informations.getIdVehicule());
                        }
                    } else {
                        throw new ClientAlreadyReservation();
                    }
                } else {
                    throw new ClientNotLegalAgeOrLicense();
                }
            } else {
                throw new ClientNotFindException(informations.getIdClient());
            }
        } catch (Exception e) {
            throw new ReservationNotAdd();
        }
    }


    @Operation(summary = "Mettre à jour une reservation dans la base de données", description = "Requête pour mettre a jour une reservation dans la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été modifié !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1\",\n" + "  \"status\": 404\n" + "}")

    }))})
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Map<String, Object>> editReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int idUSer, @Validated @RequestBody Reservations reservationsRequest) {
        try {
            List<Reservations> reservations = reservationsDao.findById(idUSer);
            if (reservations == null || reservations.isEmpty()) {
                throw new ReservationNotFindException(idUSer);
            } else {
                Map<String, Object> response = new HashMap<>();
                reservationsService.editReservation(reservations.getFirst(), reservationsRequest, reservationsDao);
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
            reservationsDao.delete(reservations.getFirst());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Votre reservations a été supprimé !");
            return ResponseEntity.ok(response);
        }
    }

    /*
    @Operation(summary = "Voir toute les reservations de la base de données ", description = "Requête pour la récupération de toute les reservations de la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class)))})
     */
    @GetMapping("/reservations/vehicle/{id}")
    public List<Reservations> reservationsVehicle(@Parameter(description = "Identifiant du véhicule", required = true) @PathVariable(value = "id") int idVehicle) {
        List<Reservations> response = reservationsDao.findByIdVehicule(idVehicle);
        if (response == null || response.isEmpty()) {
            throw new VehiculeNotReservation();
        } else {
            return response;
        }
    }
}

