package fr.vehiclerental.reservations.exception;

public class ClientNotFindException extends RuntimeException {
    public ClientNotFindException(Integer clientID) {
        super("Client not found with ID : " + clientID);
    }
}
