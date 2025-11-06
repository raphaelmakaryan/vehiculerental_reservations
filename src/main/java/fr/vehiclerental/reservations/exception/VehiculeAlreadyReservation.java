package fr.vehiclerental.reservations.exception;

public class VehiculeAlreadyReservation extends RuntimeException {
    public VehiculeAlreadyReservation() {
        super("Ce vehicule n'est pas disponible car il est deja reserver !");
    }
}
