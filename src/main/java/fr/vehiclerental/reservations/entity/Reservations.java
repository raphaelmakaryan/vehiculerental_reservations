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
@Table(name = "Reservations")
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_client")
    private int idClient;

    @Column(name = "idVehicule")
    private int idVehicule;

    @Column(name = "start_reservation")
    private LocalDate startReservation;

    @Column(name = "end_reservation")
    private LocalDate endReservation;

    @Column(name = "estimated_km")
    private int estimatedKm;

    public Reservations() {
        super();
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public LocalDate getStartReservation() {
        return startReservation;
    }

    public void setStartReservation(LocalDate startReservation) {
        this.startReservation = startReservation;
    }

    public LocalDate getEndReservation() {
        return endReservation;
    }

    public void setEndReservation(LocalDate endReservation) {
        this.endReservation = endReservation;
    }

    public int getEstimatedKm() {
        return estimatedKm;
    }

    public void setEstimatedKm(int estimatedKm) {
        this.estimatedKm = estimatedKm;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", idVehicule=" + idVehicule +
                ", startReservation=" + startReservation +
                ", endReservation=" + endReservation +
                ", estimatedKm=" + estimatedKm +
                '}';
    }
}
