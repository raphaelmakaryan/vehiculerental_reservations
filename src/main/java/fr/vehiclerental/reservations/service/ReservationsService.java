package fr.vehiclerental.reservations.service;

import fr.vehiclerental.reservations.entity.*;
import fr.vehiclerental.reservations.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service

public class ReservationsService {

    @Autowired
    ReservationsDao reservationsDao;

    /**
     * Méthode pour récuperer toute les reservations
     *
     * @return Listes des reservations
     */
    public List<ReservationsDTO> allReservation() {
        return reservationsDao.findAll().stream().map(c -> new ReservationsDTO(c.getId(), c.getIdClient(), c.getIdVehicule(), c.getStartReservation(), c.getEndReservation(), c.getEstimatedKm(), c.getPriceReservation())).collect(Collectors.toList());
    }

    /**
     * Méthode pour récuperer une reservation précis
     *
     * @param id id de la reservation
     * @return Information de la reservation
     */
    public List<ReservationsDTO> oneReservation(int id) {
        try {
            return reservationsDao.findById(id).stream().map(c -> new ReservationsDTO(c.getId(), c.getIdClient(), c.getIdVehicule(), c.getStartReservation(), c.getEndReservation(), c.getEstimatedKm(), c.getPriceReservation())).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new ReservationNotFindException(id);
        }
    }


    /**
     * Methode pour appeller l'api Client
     *
     * @param idUser Id du client demandé
     * @return Retourne la liste du client
     */
    public List<ClientDTO> requestClient(int idUser) {
        RestTemplate restTemplate = new RestTemplate();
        String userRequest = "http://localhost:8081/clients/" + idUser;
        ClientDTO[] response = restTemplate.getForObject(userRequest, ClientDTO[].class);
        if (response != null) {
            return Arrays.asList(response);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Methode de vérification si le client peut reserver ou non
     *
     * @param birthdayUser  Date d'anniverssaire du client
     * @param licenseNumber Permis du client
     * @return Vrai ou faux
     */
    public boolean canReserve(LocalDate birthdayUser, String licenseNumber) {
        if ((LocalDate.now().getYear() - birthdayUser.getYear()) >= 18 && !licenseNumber.isEmpty()) {
            return true;
        } else {
            throw new ClientNotLegalAgeOrLicense();
        }
    }

    /**
     * Methode qui appellera l'api Vehicle
     *
     * @param idVehicle Id du vehicule demandé
     * @return Retourne la liste de vehicule
     */
    public List<VehicleDTO> requestVehicle(int idVehicle) {
        RestTemplate restTemplate = new RestTemplate();
        String userRequest = "http://localhost:8082/vehicles/" + idVehicle;
        VehicleDTO[] response = restTemplate.getForObject(userRequest, VehicleDTO[].class);
        if (response == null) {
            return new ArrayList<>();
        } else {
            return Arrays.asList(response);
        }
    }

    /**
     * Methode de verification selon l'age du client et les cheveaux du vehicule
     *
     * @param birthdayClient Date de naissance du client
     * @param horsePower     Chevaux du vehicule
     * @return Vrai ou faux
     */
    public boolean requestYearClient(LocalDate birthdayClient, int horsePower) {
        int now = LocalDate.now().getYear();
        if (now - birthdayClient.getYear() < 21 && horsePower >= 8 || now - birthdayClient.getYear() > 21 && now - birthdayClient.getYear() < 25 && horsePower < 13 || now - birthdayClient.getYear() > 25) {
            return false;
        } else {
            throw new ClientAgeHorsepower();
        }
    }

    /**
     * Methode de verifiation si le client a deja une reservation ou non
     *
     * @param idClient        id du client demandé
     * @param reservationsDao Class DAO pour faire les requetes a la bdd
     * @return
     */
    public boolean clientHaveAReservation(int idClient, ReservationsDao reservationsDao) {
        if (reservationsDao.findByIdClient(idClient).isEmpty()) {
            return true;
        } else {
            throw new ClientAlreadyReservation();
        }
    }

    /**
     * Methode pour le calcul du prix final
     *
     * @param vehicle             Informations du vehicule
     * @param requiredReservation Class requiredReservation
     * @return Renvoie le prix
     */
    public int calculePriceFinal(VehicleDTO vehicle, RequiredReservation requiredReservation) {
        switch (vehicle.getType()) {
            case "car":
                return vehicle.getDefaultPrice() + vehicle.getPricePerKilometer() * requiredReservation.getEstimatedKm();
            case "utility":
                return (int) (vehicle.getDefaultPrice() + vehicle.getVolume() * 0.05 * vehicle.getPricePerKilometer() * requiredReservation.getEstimatedKm());
            case "motorcycle":
                return (int) (vehicle.getDefaultPrice() + vehicle.getCylinder() * 0.001 * vehicle.getPricePerKilometer() * requiredReservation.getEstimatedKm());
            default:
                return 0;
        }
    }

    /**
     * Methode de vérification si le vehicule a deja ete reserver selon des dates
     *
     * @param vehicle         Informations du vehicule
     * @param newReservation  Information donnée depuis la requete
     * @param reservationsDao Class DAO pour faire les requetes a la bdd
     * @return Vrai ou faux
     */
    public boolean verificationVehiculeReservation(VehicleDTO vehicle, RequiredReservation newReservation, ReservationsDao reservationsDao) {
        List<Reservations> vehicleBookings = reservationsDao.findByIdVehicule(vehicle.getId());
        LocalDate startWant = newReservation.getStartReservation();
        LocalDate endWant = newReservation.getEndReservation();
        for (Reservations booking : vehicleBookings) {
            LocalDate startBooked = booking.getStartReservation();
            LocalDate endBooked = booking.getEndReservation();
            boolean overlap =
                    !startWant.isAfter(endBooked) &&  // startWant <= endBooked
                            !endWant.isBefore(startBooked);   // endWant >= startBooked
            if (overlap) {
                throw new VehiculeAlreadyReservation();
            }
        }
        return true;
    }

    /**
     * Methode de vérification si le client existe
     *
     * @param clientRequest Information du client venant de la requete
     * @return Renvoie le client ou une erreur
     */
    public ClientDTO clientVerification(RequiredReservation clientRequest) {
        List<ClientDTO> client = this.requestClient(clientRequest.getIdClient());
        if (!client.isEmpty()) {
            return client.get(0);
        } else {
            throw new ClientNotFindException(clientRequest.getIdClient());
        }
    }

    /**
     * Methode de vérification si le véhicule existe
     *
     * @param vehicleRequest Information du véhicule venant de la requete
     * @return Renvoie le vehicule ou une erreur
     */
    public VehicleDTO vehiculeVerification(RequiredReservation vehicleRequest) {
        List<VehicleDTO> vehicule = this.requestVehicle(vehicleRequest.getIdVehicule());
        if (!vehicule.isEmpty()) {
            return vehicule.get(0);
        } else {
            throw new VehiculeNotFInd(vehicleRequest.getIdVehicule());
        }
    }

    /**
     * Methode pour crée une reservation
     *
     * @param reservationsDao Class DAO pour faire les requetes a la bdd
     * @param client          Information du client
     * @param vehicle         Information du vehicule
     * @param informations    Information donnée depuis la requete
     * @param priceFinal      Prix final
     */
    public void createReservation(ReservationsDao reservationsDao, ClientDTO client, VehicleDTO vehicle, RequiredReservation informations, int priceFinal) {
        Reservations newReservation = new Reservations();
        newReservation.setIdClient(client.getId());
        newReservation.setIdVehicule(vehicle.getId());
        newReservation.setStartReservation(informations.getStartReservation());
        newReservation.setEndReservation(informations.getEndReservation());
        newReservation.setEstimatedKm(informations.getEstimatedKm());
        newReservation.setPriceReservation(priceFinal);
        reservationsDao.save(newReservation);
    }

    /**
     * Méthode de vérification pour crée une reservation
     * @param informations Information de la requete
     * @return Réponse
     */
    public Map<String, Object> createReservationService(RequiredReservation informations) {
        ClientDTO client = this.clientVerification(informations);
        this.canReserve(client.getBirthday(), client.getNumberLicense());
        this.clientHaveAReservation(client.getId(), reservationsDao);
        VehicleDTO vehicle = this.vehiculeVerification(informations);
        this.requestYearClient(client.getBirthday(), vehicle.getHorsePower());
        this.verifMaintenance(vehicle.getId());
        this.verificationVehiculeReservation(vehicle, informations, reservationsDao);
        int priceFinal = this.calculePriceFinal(vehicle, informations);
        if (priceFinal != 0) {
            Map<String, Object> response = new HashMap<>();
            this.createReservation(reservationsDao, client, vehicle, informations, priceFinal);
            response.put("success", true);
            response.put("message", "Votre reservation a été ajoutée !");
            return response;
        } else {
            throw new VehicleTypeKnowName();
        }
    }


    /**
     * Methode pour modifier une reservation
     *
     * @param findindReservation     Information de la reservation
     * @param reservationBodyRequest Informations issue de reservation récuperer de la requete
     * @param reservationsDao        Class DAO pour faire les requetes a la bdd
     */
    public void editReservation(Reservations findindReservation, Reservations reservationBodyRequest, ReservationsDao reservationsDao) {
        findindReservation.setIdClient(reservationBodyRequest.getIdClient());
        findindReservation.setIdVehicule(reservationBodyRequest.getIdVehicule());
        findindReservation.setStartReservation(reservationBodyRequest.getStartReservation());
        findindReservation.setEndReservation(reservationBodyRequest.getEndReservation());
        findindReservation.setEstimatedKm(reservationBodyRequest.getEstimatedKm());
        findindReservation.setPriceReservation(reservationBodyRequest.getPriceReservation());
        reservationsDao.save(findindReservation);
    }

    /**
     * Méthode de vérrification pour modifier une reservation
     * @param idUser id du client
     * @param reservationsRequest Information de la requete
     * @return Reponse
     */
    public Map<String, Object> editReservationService(int idUser, Reservations reservationsRequest) {
        try {
            List<Reservations> reservations = reservationsDao.findById(idUser);
            if (reservations == null || reservations.isEmpty()) {
                throw new ReservationNotFindException(idUser);
            } else {
                Map<String, Object> response = new HashMap<>();
                this.editReservation(reservations.getFirst(), reservationsRequest, reservationsDao);
                response.put("success", true);
                response.put("message", "Votre reservation a été modifié !");
                return response;
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Méthode de verification pour supprimer une reservation
     * @param idUSer id du client
     * @return Réponse
     */
    public Map<String, Object> deleteReservationService(int idUSer) {
        List<Reservations> reservations = reservationsDao.findById(idUSer);
        if (reservations == null || reservations.isEmpty()) {
            throw new ReservationNotFindException(idUSer);
        } else {
            reservationsDao.delete(reservations.getFirst());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Votre reservations a été supprimé !");
            return response;
        }
    }

    /**
     * Methode de vérification si le véhicule est en entretien
     *
     * @param idVehicle Id du vehicule
     * @return Vrai ou erreur
     */
    public boolean verifMaintenance(int idVehicle) {
        RestTemplate restTemplate = new RestTemplate();
        String maintenanceRequest = "http://localhost:8084/maintenance/vehicle/" + idVehicle;
        MaintenanceDTO[] response = restTemplate.getForObject(maintenanceRequest, MaintenanceDTO[].class);
        if (response.length == 0) {
            return true;
        } else {
            throw new ReservationNotAdd();
        }
    }
}