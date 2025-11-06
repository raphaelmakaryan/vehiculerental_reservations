package fr.vehiclerental.reservations.exception;

public class ReservationNotAdd extends RuntimeException {
    public ReservationNotAdd() {
        super("Votre reservations n'a pas été ajoutée !");
    }
}
