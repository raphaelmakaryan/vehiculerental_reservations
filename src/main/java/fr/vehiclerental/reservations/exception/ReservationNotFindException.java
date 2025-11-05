package fr.vehiclerental.reservations.exception;

public class ReservationNotFindException extends RuntimeException {
    public ReservationNotFindException(Integer reservationId) {
        super("Reservation not found with ID : " + reservationId);
    }
}
