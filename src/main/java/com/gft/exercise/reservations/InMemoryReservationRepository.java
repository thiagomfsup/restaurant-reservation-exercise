package com.gft.exercise.reservations;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<UUID, Set<Reservation>> restaurantReservationMap = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {

        restaurantReservationMap
                .computeIfAbsent(reservation.restaurantId(), ignored -> new HashSet<>())
                .add(reservation);

        return reservation;
    }

    @Override
    public Set<Reservation> getByRestaurantAndDate(UUID restaurantId, LocalDate localDate) {
        return restaurantReservationMap.getOrDefault(restaurantId, Set.of()).stream()
                .filter(reservation -> reservation.dateTime().toLocalDate().isEqual(localDate))
                .collect(Collectors.toSet());
    }
}
