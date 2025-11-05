package fr.vehiclerental.reservations.entity;

import java.time.LocalDate;

public class ClientDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String number_license;
    private LocalDate birthday;
    private int obtaining_license;

    public ClientDTO() {
    }

    public ClientDTO(Integer id, String firstName, String lastName, String number_license, LocalDate birthday, int obtaining_license) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number_license = number_license;
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
        return number_license;
    }

    public void setNumberlicense(String license) {
        this.number_license = number_license;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getObtaining_license() {
        return obtaining_license;
    }

    public void setObtaining_license(int obtaining_license) {
        this.obtaining_license = obtaining_license;
    }
}
