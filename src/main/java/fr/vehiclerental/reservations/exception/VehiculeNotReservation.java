package fr.vehiclerental.reservations.exception;

public class VehiculeNotReservation extends RuntimeException {
    public VehiculeNotReservation() {
        super("Ce véhicule ne contient pas de reservation !");
    }
}
