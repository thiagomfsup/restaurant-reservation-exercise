package com.gft.exercise.reservations;

import com.gft.exercise.restaurants.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationManagerImpl implements ReservationManager {

    private final RestaurantRepository restaurantRepository;

    private final ReservationRepository reservationRepository;

    public ReservationManagerImpl(RestaurantRepository restaurantRepository, ReservationRepository reservationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation add(UUID restaurantId, String customerName, String customerPhone, int partySize, LocalDateTime dateTime) {
        final var restaurant = restaurantRepository.getById(restaurantId);

        if (restaurant == null)
            throw new NoSuchElementException("Restaurant ID %s doesn't exist".formatted(restaurantId));

        if (restaurant.closingDays().contains(dateTime.toLocalDate()))
            throw new IllegalArgumentException("Cannot make a reservation when the restaurant is closed");

        if (dateTime.toLocalTime().isBefore(restaurant.open()))
            throw new IllegalArgumentException("Cannot make a reservation before restaurant's opening hour");

        if (dateTime.toLocalTime().isAfter(restaurant.close()))
            throw new IllegalArgumentException("Cannot make a reservation after restaurant's closing hour");

        final var newReservation = Reservation.builder()
                .setRestaurantId(restaurantId)
                .setCustomerName(customerName)
                .setCustomePhoneNumber(customerPhone)
                .setPartySize(partySize)
                .setDateTime(dateTime)
                .build();

        final var overlapPartySize = getReservationBetween(restaurantId, dateTime).stream()
                .filter(reservation -> reservation.overlap(newReservation))
                .mapToInt(Reservation::partySize).sum();

        if (overlapPartySize + newReservation.partySize() >= restaurant.capacity())
            throw new IllegalArgumentException("Restaurant is full");

        return reservationRepository.save(newReservation);
    }

    private Set<Reservation> getReservationBetween(UUID restaurantId, LocalDateTime dateTime) {
        return reservationRepository.getByRestaurantAndDate(restaurantId, dateTime.toLocalDate());
    }
}
