package fr.vehiclerental.reservations.entity;

import java.time.LocalDate;

public class VehicleDTO {
    private int id;
    private String type;
    private String model;
    private String color;
    private String registration;
    private int horsePower;
    private Integer cylinder;
    private Integer volume;
    private int pricePerKilometer;
    private int defaultPrice;

    public VehicleDTO(int id, String type, String model, String color, String registration, int horsePower, int pricePerKilometer, int volume, int cylinder, int defaultPrice) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.color = color;
        this.registration = registration;
        this.horsePower = horsePower;
        this.volume = volume;
        this.cylinder = cylinder;
        this.pricePerKilometer = pricePerKilometer;
        this.defaultPrice = defaultPrice;
    }

    public int getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(int defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public int getPricePerKilometer() {
        return pricePerKilometer;
    }

    public void setPricePerKilometer(int pricePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setCylinder(Integer cylinder) {
        this.cylinder = cylinder;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getCylinder() {
        return cylinder;
    }

    public void setCylinder(int cylinder) {
        this.cylinder = cylinder;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", registration='" + registration + '\'' +
                ", horsePower=" + horsePower +
                ", cylinder=" + cylinder +
                ", volume=" + volume +
                ", pricePerKilometer=" + pricePerKilometer +
                ", defaultPrice=" + defaultPrice +
                '}';
    }
}
