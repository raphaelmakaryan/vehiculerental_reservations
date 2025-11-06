package fr.vehiclerental.reservations.exception;

public class ClientAlreadyReservation extends RuntimeException {
    public ClientAlreadyReservation() {
        super("Ce client dispose déjà d'une réservation en cours");
    }
}
