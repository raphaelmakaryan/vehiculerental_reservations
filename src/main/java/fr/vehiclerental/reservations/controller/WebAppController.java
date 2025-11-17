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

import java.util.List;
import java.util.Map;

@RestController
public class WebAppController {
    private final ReservationsService reservationsService;
    private final ReservationsDao reservationsDao;

    public WebAppController(ReservationsService reservationsService, ReservationsDao reservationsDao) {
        this.reservationsService = reservationsService;
        this.reservationsDao = reservationsDao;
    }

    @Operation(summary = "Home page")
    @RequestMapping("/")
    public String index() {
        return "Welcome to the Vehicle Rental company's Reservation API!";
    }

    @Operation(summary = "Voir toute les reservations de la base de données ", description = "Requête pour la récupération de toute les reservations de la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class)))})
    @GetMapping("/reservations")
    public List<ReservationsDTO> reservations() {
        return reservationsService.allReservation();
    }

    @Operation(summary = "Voir une reservation spécifique de la base de données", description = "Requête pour la récupération d'une reservation de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1 \",\n" + "  \"status\": 404\n" + "}")}))})
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.GET)
    public List<ReservationsDTO> getReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int id) {
        return reservationsService.oneReservation(id);
    }

    @Operation(summary = "Crée une nouvelle reservation dans la base de données", description = "Requête pour crée/ajouter une reservation dans la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservations a été ajoutée !\"\n" + "}"))),
            @ApiResponse(responseCode = "405", description = "Erreur métier", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
                    examples = {@ExampleObject(name = "Client introuvable", value = """
                            {
                              "timestamp": "2025-11-06T15:00:00",
                              "status": 404,
                              "error": "Client introuvable",
                              "message": "Client not found with ID : 1"
                            }
                            """), @ExampleObject(name = "Client déjà réservé", value = """
                            {
                              "timestamp": "2025-11-06T15:00:00",
                              "status": 404,
                              "error": "Client déjà réservé",
                              "message": "Ce client dispose déjà d'une réservation en cours"
                            }
                            """), @ExampleObject(name = "Véhicule déjà réservé", value = """
                            {
                              "timestamp": "2025-11-06T15:00:00",
                              "status": 404,
                              "error": "Véhicule déjà réservé",
                              "message": "Le véhicule 12 est déjà réservé pour cette période"
                            }
                            """)}))})
    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addReservations(@Validated @RequestBody RequiredReservation informations) {
        return ResponseEntity.ok(reservationsService.createReservationService(informations));
    }

    @Operation(summary = "Mettre à jour une reservation dans la base de données", description = "Requête pour mettre a jour une reservation dans la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été modifié !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1\",\n" + "  \"status\": 404\n" + "}")}))})
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Map<String, Object>> editReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int idUSer, @Validated @RequestBody Reservations reservationsRequest) {
        return ResponseEntity.ok(reservationsService.editReservationService(idUSer, reservationsRequest));
    }

    @Operation(summary = "Supprimer une reservation de la base de données", description = "Requête pour supprimer une reservations de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été supprimé !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1 \",\n" + "  \"status\": 404\n" + "}")}))})
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Map<String, Object>> deleteReservations(@Parameter(description = "Identifiant de la reservation", required = true) @PathVariable(value = "id") int idUSer) {
        return ResponseEntity.ok(reservationsService.deleteReservationService(idUSer));
    }

    @Operation(summary = "Voir une reservation via l'id du véhicule de la base de données ", description = "Requête pour la récupération d'une reservation via l'id du véhicule de la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reservations.class)))})
    @GetMapping("/reservations/vehicle/{id}")
    public List<Reservations> reservationsVehicle(@Parameter(description = "Identifiant du véhicule", required = true) @PathVariable(value = "id") int idVehicle) {
        return reservationsDao.findByIdVehicule(idVehicle);
    }
}

