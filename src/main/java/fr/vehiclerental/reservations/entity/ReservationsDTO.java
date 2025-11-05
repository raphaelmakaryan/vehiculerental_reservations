package fr.vehiclerental.reservations.entity;

import java.time.LocalDate;

public class ReservationsDTO {
    private Integer id;
    private int idClient;
    private int idVehicule;
    private LocalDate startReservation;
    private LocalDate endReservation;
    private int estimatedKm;

    public ReservationsDTO() {
    }

    public ReservationsDTO(Integer id, int idClient, int idVehicule, LocalDate startReservation, LocalDate endReservation, int estimatedKm) {
        this.id = id;
        this.idClient = idClient;
        this.idVehicule = idVehicule;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        this.estimatedKm = estimatedKm;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }


    public int getIdClient() {
        return idClient;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public LocalDate getStartReservation() {
        return startReservation;
    }

    public void setStartReservation(LocalDate startReservation) {
        this.startReservation = startReservation;
    }

    public LocalDate getEndReservation() {
        return endReservation;
    }

    public void setEndReservation(LocalDate endReservation) {
        this.endReservation = endReservation;
    }

    public int getEstimatedKm() {
        return estimatedKm;
    }

    public void setEstimatedKm(int estimatedKm) {
        this.estimatedKm = estimatedKm;
    }
}
