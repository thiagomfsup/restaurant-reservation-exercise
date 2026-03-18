package com.gft.service;

import com.gft.domain.Reservation;
import com.gft.domain.ReservationNotFoundException;
import com.gft.domain.ReservationNotValidException;
import com.gft.domain.Restaurant;
import com.gft.domain.RestaurantNotFoundException;
import com.gft.repository.InMemoryReservationRepository;
import com.gft.repository.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationServiceTest {

    private ReservationService reservationService;
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantService(new InMemoryRestaurantRepository());
        reservationService = new ReservationService(restaurantService, new InMemoryReservationRepository());

        restaurantService.addRestaurant(Restaurant.builder()
                .id("1")
                .name("La Pepica")
                .capacity(10)
                .openingTime(LocalTime.of(13, 0))
                .closingTime(LocalTime.of(23, 0))
                .closingDays(Set.of(DayOfWeek.MONDAY))
                .build());
    }

    @Test
    void shouldAddReservation() {
        Reservation reservation = buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 17, 14, 0));

        reservationService.addReservation(reservation);

        assertThat(reservationService.findById("res-1")).isPresent();
    }

    @Test
    void shouldDeleteReservation() {
        reservationService.addReservation(buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 17, 14, 0)));

        reservationService.deleteReservation("res-1");

        assertThat(reservationService.findById("res-1")).isEmpty();
    }

    @Test
    void shouldUpdateReservation() {
        reservationService.addReservation(buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 17, 14, 0)));

        Reservation updated = buildReservation("res-1", "1", 6, LocalDateTime.of(2026, 3, 17, 15, 0));
        reservationService.updateReservation(updated);

        assertThat(reservationService.findById("res-1").get().getPartySize()).isEqualTo(6);
    }

    @Test
    void shouldThrowWhenReservationIsNull() {
        assertThatThrownBy(() -> reservationService.addReservation(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowWhenRestaurantDoesNotExist() {
        Reservation reservation = buildReservation("res-1", "no-existe", 4, LocalDateTime.of(2026, 3, 17, 14, 0));

        assertThatThrownBy(() -> reservationService.addReservation(reservation))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void shouldThrowWhenReservationIsOnClosingDay() {
        Reservation reservation = buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 16, 14, 0));

        assertThatThrownBy(() -> reservationService.addReservation(reservation))
                .isInstanceOf(ReservationNotValidException.class);
    }

    @Test
    void shouldThrowWhenReservationStartsBeforeOpeningTime() {
        Reservation reservation = buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 17, 12, 0));

        assertThatThrownBy(() -> reservationService.addReservation(reservation))
                .isInstanceOf(ReservationNotValidException.class);
    }

    @Test
    void shouldThrowWhenReservationEndsAfterClosingTime() {
        Reservation reservation = buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 17, 22, 0));

        assertThatThrownBy(() -> reservationService.addReservation(reservation))
                .isInstanceOf(ReservationNotValidException.class);
    }

    @Test
    void shouldThrowWhenCapacityExceeded() {
        reservationService.addReservation(buildReservation("res-1", "1", 8, LocalDateTime.of(2026, 3, 17, 14, 0)));

        assertThatThrownBy(() -> reservationService.addReservation(
                buildReservation("res-2", "1", 4, LocalDateTime.of(2026, 3, 17, 14, 0))))
                .isInstanceOf(ReservationNotValidException.class);
    }

    @Test
    void shouldAllowReservationsWithoutOverlap() {
        reservationService.addReservation(buildReservation("res-1", "1", 8, LocalDateTime.of(2026, 3, 17, 14, 0)));
        reservationService.addReservation(buildReservation("res-2", "1", 8, LocalDateTime.of(2026, 3, 17, 16, 0)));

        assertThat(reservationService.findById("res-2")).isPresent();
    }

    @Test
    void shouldThrowWhenDeletingNonExistentReservation() {
        assertThatThrownBy(() -> reservationService.deleteReservation("no-existe"))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentReservation() {
        assertThatThrownBy(() -> reservationService.updateReservation(
                buildReservation("no-existe", "1", 4, LocalDateTime.of(2026, 3, 17, 14, 0))))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void shouldThrowWhenUpdatingNullReservation() {
        assertThatThrownBy(() -> reservationService.updateReservation(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldDetectOverlappingReservations() {
        reservationService.addReservation(buildReservation("res-1", "1", 3, LocalDateTime.of(2026, 3, 17, 14, 0)));

        assertThatThrownBy(() -> reservationService.addReservation(
                buildReservation("res-2", "1", 8, LocalDateTime.of(2026, 3, 17, 15, 0))))
                .isInstanceOf(ReservationNotValidException.class);
    }

    @Test
    void shouldReturnCustomerDetails() {
        Reservation reservation = buildReservation("res-1", "1", 4, LocalDateTime.of(2026, 3, 17, 14, 0));
        reservationService.addReservation(reservation);

        Reservation saved = reservationService.findById("res-1").get();
        assertThat(saved.getCustomerName()).isEqualTo("Pau Lopez");
        assertThat(saved.getCustomerPhone()).isEqualTo("600000000");
    }

    @Test
    void shouldThrowWhenReservationEndsAfterClosingTimeNotMidnight() {
        Reservation reservation = buildReservation("res-98", "1", 2, LocalDateTime.of(2026, 3, 17, 21, 30));

        assertThatThrownBy(() -> reservationService.addReservation(reservation))
                .isInstanceOf(ReservationNotValidException.class);
    }

    @Test
    void shouldNotOverlapWhenFirstReservationStartsAfterSecondEnds() {
        reservationService.addReservation(buildReservation("res-1", "1", 5, LocalDateTime.of(2026, 3, 17, 18, 0)));
        reservationService.addReservation(buildReservation("res-2", "1", 5, LocalDateTime.of(2026, 3, 17, 14, 0)));

        assertThat(reservationService.findById("res-2")).isPresent();
    }

    private Reservation buildReservation(String id, String restaurantId, int partySize, LocalDateTime startTime) {
        return Reservation.builder()
                .id(id)
                .restaurantId(restaurantId)
                .customerName("Pau Lopez")
                .customerPhone("600000000")
                .partySize(partySize)
                .startTime(startTime)
                .build();
    }
}