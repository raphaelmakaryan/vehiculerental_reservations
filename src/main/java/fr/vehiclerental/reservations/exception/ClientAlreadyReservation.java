package fr.vehiclerental.reservations.exception;

public class ClientAlreadyReservation extends RuntimeException {
    public ClientAlreadyReservation() {
        super("Le client a deja reservé un vehicule !");
    }
}
