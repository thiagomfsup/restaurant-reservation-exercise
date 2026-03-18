package com.gft.service;

import com.gft.domain.Reservation;
import com.gft.domain.ReservationNotFoundException;
import com.gft.domain.ReservationNotValidException;
import com.gft.domain.Restaurant;
import com.gft.domain.RestaurantNotFoundException;
import com.gft.repository.ReservationRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ReservationService {

    private final RestaurantService restaurantService;
    private final ReservationRepository repository;

    public ReservationService(RestaurantService restaurantService, ReservationRepository repository) {
        this.restaurantService = restaurantService;
        this.repository = repository;
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("La reserva no puede ser null");
        }

        Restaurant restaurant = restaurantService.findById(reservation.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(reservation.getRestaurantId()));

        validateDay(reservation, restaurant);
        validateHours(reservation, restaurant);
        validateCapacity(reservation, restaurant);

        repository.save(reservation);
    }

    public void deleteReservation(String id) {
        repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
        repository.delete(id);
    }

    public void updateReservation(Reservation updated) {
        if (updated == null) {
            throw new IllegalArgumentException("La reserva no puede ser null");
        }

        repository.findById(updated.getId())
                .orElseThrow(() -> new ReservationNotFoundException(updated.getId()));

        Restaurant restaurant = restaurantService.findById(updated.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(updated.getRestaurantId()));

        validateDay(updated, restaurant);
        validateHours(updated, restaurant);
        validateCapacity(updated, restaurant);

        repository.save(updated);
    }

    public Optional<Reservation> findById(String id) {
        return repository.findById(id);
    }

    private void validateDay(Reservation reservation, Restaurant restaurant) {
        var day = reservation.getStartTime().getDayOfWeek();
        if (restaurant.getClosingDays().contains(day)) {
            throw new ReservationNotValidException("El restaurante está cerrado ese día: " + day);
        }
    }

    private void validateHours(Reservation reservation, Restaurant restaurant) {
        var startTime = reservation.getStartTime().toLocalTime();
        var endTime = reservation.getEndTime().toLocalTime();

        if (startTime.isBefore(restaurant.getOpeningTime())) {
            throw new ReservationNotValidException("La reserva empieza antes de que abra el restaurante");
        }

        boolean endsAtMidnight = endTime.equals(LocalTime.MIDNIGHT);
        if (endsAtMidnight || endTime.isAfter(restaurant.getClosingTime())) {
            throw new ReservationNotValidException("La reserva termina después de que cierre el restaurante");
        }
    }

    private void validateCapacity(Reservation reservation, Restaurant restaurant) {
        int occupiedSeats = getOverlappingReservations(reservation).stream()
                .mapToInt(Reservation::getPartySize)
                .sum();

        if (occupiedSeats + reservation.getPartySize() > restaurant.getCapacity()) {
            throw new ReservationNotValidException("No hay suficiente capacidad en el restaurante");
        }
    }

    private List<Reservation> getOverlappingReservations(Reservation newReservation) {
        return repository.findAll().stream()
                .filter(r -> r.getRestaurantId().equals(newReservation.getRestaurantId()))
                .filter(r -> !r.getId().equals(newReservation.getId()))
                .filter(r -> overlaps(r, newReservation))
                .toList();
    }

    private boolean overlaps(Reservation a, Reservation b) {
        return a.getStartTime().isBefore(b.getEndTime())
                && b.getStartTime().isBefore(a.getEndTime());
    }
}