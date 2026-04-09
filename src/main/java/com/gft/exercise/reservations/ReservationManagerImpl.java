package com.gft.exercise.reservations;

import com.gft.exercise.restaurants.Restaurant;
import com.gft.exercise.restaurants.RestaurantRepository;

import java.time.LocalDate;
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

        final var reservation = Reservation.builder()
                .setRestaurantId(restaurantId)
                .setCustomerName(customerName)
                .setCustomePhoneNumber(customerPhone)
                .setPartySize(partySize)
                .setDateTime(dateTime)
                .build();

        final var reservationBook = this.reservationBookForRestaurantAndDate(restaurant, dateTime.toLocalDate());

        reservationBook.addReservation(reservation);

        return reservationRepository.save(reservation);
    }

    private ReservationBook reservationBookForRestaurantAndDate(Restaurant restaurant, LocalDate date) {
        final var reservations = reservationRepository.getByRestaurantAndDate(restaurant.id(), date);
        return new ReservationBook(restaurant, date, reservations);
    }
}
