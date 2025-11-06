package fr.vehiclerental.reservations.exception;

public class ClientAgeHorsepower extends RuntimeException {
    public ClientAgeHorsepower() {
        super("Le vehicule défini dans la requete n'est pas disponible selon l'age du client et les cheveaux du vehicule !");
    }
}
