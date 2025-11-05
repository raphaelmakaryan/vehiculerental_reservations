package fr.vehiclerental.reservations.service;

import fr.vehiclerental.reservations.entity.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationsDao extends JpaRepository<Reservations, Integer> {
    List<Reservations> findById(int id);
    List<Reservations> findAll();
    void delete(Reservations client);
    Reservations save(Reservations client);
}