package fr.vehiclerental.reservations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "number_license")
    private String number_license;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "obtaining_license")
    private int obtaining_license;

    public Client() {
        super();
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getNumberLicense() {
        return number_license;
    }

    public void setNumberlicense(String number_license) {
        this.number_license = number_license;
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

    public void setObtaining_license(int obtaining_license) {
        this.obtaining_license = obtaining_license;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", number_license='" + number_license + '\'' +
                ", birthday=" + birthday +
                ", obtaining_license=" + obtaining_license +
                '}';
    }
}
