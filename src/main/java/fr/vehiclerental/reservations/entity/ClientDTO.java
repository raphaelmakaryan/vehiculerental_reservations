package fr.vehiclerental.reservations.entity;

import java.time.LocalDate;

public class ClientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String numberLicense;
    private LocalDate birthday;
    private int obtaining_license;

    public ClientDTO() {
    }

    public ClientDTO(Integer id, String firstName, String lastName, String numberLicense, LocalDate birthday, int obtaining_license) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberLicense = numberLicense;
        this.birthday = birthday;
        this.obtaining_license = obtaining_license;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumberLicense() {
        return numberLicense;
    }

    public void setNumberlicense(String license) {
        this.numberLicense = numberLicense;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getObtainingLicense() {
        return obtaining_license;
    }

    public void setObtainingLicense(int obtaining_license) {
        this.obtaining_license = obtaining_license;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", numberLicense='" + numberLicense + '\'' +
                ", birthday=" + birthday +
                ", obtaining_license=" + obtaining_license +
                '}';
    }
}