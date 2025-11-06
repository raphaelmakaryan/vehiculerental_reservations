package fr.vehiclerental.reservations.exception;

public class VehiculeNotFInd extends RuntimeException {
    public VehiculeNotFInd(int id) {
        super("Le vehicule a l'id : " + id + " n'existe pas !");
    }
}
