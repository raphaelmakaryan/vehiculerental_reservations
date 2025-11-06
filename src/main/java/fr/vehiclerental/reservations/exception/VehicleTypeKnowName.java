package fr.vehiclerental.reservations.exception;

public class VehicleTypeKnowName extends RuntimeException {
    public VehicleTypeKnowName() {
        super("Le véhicule ne fais pas partis des types démandés : car | motorcycle | utility");
    }
}
