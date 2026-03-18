package com.gft.repository;

import com.gft.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    void save(Reservation reservation);

    void delete(String id);

    Optional<Reservation> findById(String id);

    List<Reservation> findAll();
}