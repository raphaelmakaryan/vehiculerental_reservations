package fr.vehiclerental.reservations.exception;

public class VehiculeNotReservation extends RuntimeException {
    public VehiculeNotReservation() {
        super("Le véhicule n'est  ");
    }
}
