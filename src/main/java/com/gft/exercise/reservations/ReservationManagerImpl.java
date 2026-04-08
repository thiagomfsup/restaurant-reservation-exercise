package com.gft.exercise.reservations;

import com.gft.exercise.restaurants.RestaurantRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

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

        final var newReservation = Reservation.builder()
                .setRestaurantId(restaurantId)
                .setCustomerName(customerName)
                .setCustomePhoneNumber(customerPhone)
                .setPartySize(partySize)
                .setDateTime(dateTime)
                .build();

        restaurant.validate(newReservation);

        final var overlapPartySize = getReservationBetween(restaurantId, dateTime).stream()
                .filter(reservation -> reservation.overlap(newReservation))
                .mapToInt(Reservation::partySize).sum();

        if (overlapPartySize + newReservation.partySize() > restaurant.capacity())
            throw new IllegalArgumentException("Restaurant is full");

        return reservationRepository.save(newReservation);
    }

    private Set<Reservation> getReservationBetween(UUID restaurantId, LocalDateTime dateTime) {
        return reservationRepository.getByRestaurantAndDate(restaurantId, dateTime.toLocalDate());
    }
}
