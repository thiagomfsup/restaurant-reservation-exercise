package com.gft.exercise.reservations;

import com.gft.exercise.restaurants.InMemoryRestaurantRepository;
import com.gft.exercise.restaurants.Restaurant;
import com.gft.exercise.restaurants.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ReservationManagerImplTest {

    @Spy
    private RestaurantRepository restaurantRepository = new InMemoryRestaurantRepository();

    @Spy
    private ReservationRepository reservationRepository = new InMemoryReservationRepository();

    @InjectMocks
    private ReservationManagerImpl reservationManager;

    private final Restaurant restaurant = Restaurant.builder()
            .name("A name")
            .openHour(LocalTime.of(12, 0))
            .closeHour(LocalTime.of(23, 0))
            .capacity(10)
            .closingDays(Set.of(LocalDate.of(2026, 4, 6)))
            .build();

    @BeforeEach
    void setUp() {
        restaurantRepository.save(restaurant);
    }

    @AfterEach
    void tearDown() {
        restaurantRepository.delete(restaurant.id());
    }

    @Test
    void shouldFailWhenRestaurantIdIsNull() {
        assertThatThrownBy(() -> reservationManager.add(null,
                "customerName", "123456789", 2, LocalDateTime.now()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldFailWhenRestaurantDoesntExist() {
        UUID invalidRestaurantId = UUID.randomUUID();
        assertThatThrownBy(() -> reservationManager.add(invalidRestaurantId,
                "customerName", "123456789", 2, LocalDateTime.now()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void shouldNotAllowReservationWhenTimeIsBeforeRestaurantOpeningHour() {
        assertThatThrownBy(() -> reservationManager.add(restaurant.id(),
                "customerName", "123456789", 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("before restaurant's opening hour");
    }

    @Test
    void shouldNotAllowReservationWhenTimeIsAfterRestaurantClosingHour() {
        assertThatThrownBy(() -> reservationManager.add(restaurant.id(),
                "customerName", "123456789", 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 30))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("after restaurant's closing hour");
    }
    
    @Test
    void shouldNotAllowReservationWhenRestaurantIsClose() {
        final var anyRestaurantClosingDay = restaurant.closingDays().stream().findAny().orElseThrow();
        assertThatThrownBy(() -> reservationManager.add(restaurant.id(),
                "customerName", "123456789", 2, LocalDateTime.of(anyRestaurantClosingDay, LocalTime.now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("when the restaurant is closed");
    }

    @Test
    void shouldNotAllowReservationWhichOverlapReservationsExceedRestaurantCapacity() {
        reservationManager.add(restaurant.id(),"customerName", "123456789", 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
        reservationManager.add(restaurant.id(),"customerName", "123456789", 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        reservationManager.add(restaurant.id(),"customerName", "123456789", 4, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));

        assertThatThrownBy(() -> reservationManager.add(restaurant.id(),
                "customerName", "123456789", 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Restaurant is full");
    }

    @Test
    void shouldRegisterReservation() {
        reservationManager.add(restaurant.id(),"customerName", "123456789", 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));
        reservationManager.add(restaurant.id(),"customerName", "123456789", 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        reservationManager.add(restaurant.id(),"customerName", "123456789", 4, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)));

        final var reservation = reservationManager.add(restaurant.id(), "customerName", "123456789", 4, LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0)));

        assertThat(reservation).isNotNull();
    }

}