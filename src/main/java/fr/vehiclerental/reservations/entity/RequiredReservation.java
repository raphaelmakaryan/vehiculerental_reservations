package fr.vehiclerental.reservations.entity;

import java.time.LocalDate;

public class RequiredReservation {
    private int idClient;
    private int idVehicule;
    private LocalDate startReservation;
    private LocalDate endReservation;
    private int estimatedKm;

    public RequiredReservation() {
    }

    public RequiredReservation(int idClient, int idVehicule,
                               LocalDate startReservation,
                               LocalDate endReservation,
                               int estimatedKm) {
        this.idClient = idClient;
        this.idVehicule = idVehicule;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
        this.estimatedKm = estimatedKm;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getEstimatedKm() {
        return estimatedKm;
    }

    public void setEstimatedKm(int estimatedKm) {
        this.estimatedKm = estimatedKm;
    }

    public LocalDate getEndReservation() {
        return endReservation;
    }

    public void setEndReservation(LocalDate endReservation) {
        this.endReservation = endReservation;
    }

    public LocalDate getStartReservation() {
        return startReservation;
    }

    public void setStartReservation(LocalDate startReservation) {
        this.startReservation = startReservation;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }
}