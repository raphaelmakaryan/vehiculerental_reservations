package fr.vehiclerental.reservations.exception;

public class ClientNotLegalAgeOrLicense extends RuntimeException {
    public ClientNotLegalAgeOrLicense() {
        super("Le client n'a pas l'age légale ou de permis valable !");
    }
}
