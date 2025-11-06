package fr.vehiclerental.reservations.exception;

public class VehiculeAlreadyReservation extends RuntimeException {
    public VehiculeAlreadyReservation() {
        super("Le véhicule est déjà réservé pour cette période");
    }
}
