package com.gft.exercise.reservations;

import com.gft.exercise.restaurants.Restaurant;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ReservationBook {
    private final Restaurant restaurant;
    private final LocalDate date;
    private final Set<Reservation> reservations;

    public ReservationBook(Restaurant restaurant, LocalDate date, Set<Reservation> reservations) {
        this.restaurant = restaurant;
        this.date = date;
        this.reservations = new HashSet<>(reservations);
    }

    public void addReservation(Reservation reservation) {
        restaurant.validate(reservation);

        final var overlapPartySize = this.reservations.stream()
                .filter(reservation::overlap)
                .mapToInt(Reservation::partySize)
                .sum();

        if (overlapPartySize + reservation.partySize() > restaurant.capacity())
            throw new IllegalArgumentException("Restaurant is full");
    }
}
