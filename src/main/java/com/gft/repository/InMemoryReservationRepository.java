package com.gft.repository;

import com.gft.domain.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<String, Reservation> storage = new HashMap<>();

    @Override
    public void save(Reservation reservation) {
        storage.put(reservation.getId(), reservation);
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }

    @Override
    public Optional<Reservation> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(storage.values());
    }
}