package com.gft.exercise.reservations;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Set<Reservation> getByRestaurantAndDate(UUID restaurantId, LocalDate localDate);
}
