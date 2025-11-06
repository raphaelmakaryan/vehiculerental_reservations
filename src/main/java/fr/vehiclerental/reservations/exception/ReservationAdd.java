package fr.vehiclerental.reservations.exception;

public class ReservationAdd extends RuntimeException {
    public ReservationAdd() {
        super("Votre reservations a été ajoutée !");
    }
}
