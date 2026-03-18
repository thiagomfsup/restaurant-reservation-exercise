package Reservation;

import Restaurant.Restaurant;
import Restaurant.RestaurantManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagementTest {

    private ReservationManagement reservationManagement;
    private RestaurantManagement restaurantManagement;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        restaurantManagement = new RestaurantManagement();
        reservationManagement = new ReservationManagement(restaurantManagement);

        Map<DayOfWeek, LocalTime[]> schedule = new HashMap<>() {{
            put(DayOfWeek.MONDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.TUESDAY,   new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.THURSDAY,  new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.FRIDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(0, 0)});
            put(DayOfWeek.SATURDAY,  new LocalTime[]{LocalTime.of(12, 0), LocalTime.of(0, 0)});
        }};

        Restaurant restaurant = restaurantManagement.addRestaurant("Prueba", 20, schedule, DayOfWeek.SUNDAY);
        restaurantId = restaurant.getId();
    }

    @Test
    void shouldGenerateUniqueId() {
        Reservation r1 = reservationManagement.addReservation(restaurantId, "Juan", "123", 4, LocalDateTime.of(2026, 3, 23, 14, 0));
        Reservation r2 = reservationManagement.addReservation(restaurantId, "Ana", "456", 4, LocalDateTime.of(2026, 3, 23, 16, 0));

        assertNotEquals(r1.getReservationId(), r2.getReservationId());
    }

    @Test
    void restaurantShouldExist() {
        Reservation r = reservationManagement.addReservation(
                UUID.randomUUID(), "Juan", "123", 4,
                LocalDateTime.of(2026, 3, 23, 14, 0)
        );

        assertNull(r);
    }

    @Test
    void shouldBeNotBeforeOpening() {
        Reservation r = reservationManagement.addReservation(
                restaurantId, "Juan", "123", 4,
                LocalDateTime.of(2026, 3, 23, 10, 0) // Monday, opens at 13:00
        );

        assertNull(r);
    }

    @Test
    void shouldNotExceedCapacity() {
        reservationManagement.addReservation(restaurantId, "Juan", "123", 10, LocalDateTime.of(2026, 3, 23, 14, 0));
        reservationManagement.addReservation(restaurantId, "Ana", "456", 10, LocalDateTime.of(2026, 3, 23, 14, 0));

        Reservation r = reservationManagement.addReservation(
                restaurantId, "Pedro", "789", 1,
                LocalDateTime.of(2026, 3, 23, 14, 0)
        );

        assertNull(r);
    }

    @Test
    void shouldExistWhenDeleting() {
        assertFalse(reservationManagement.deleteReservation(UUID.randomUUID()));
    }

    @Test
    void shouldExistRestaurantWhenModifying() {
        Reservation updated = reservationManagement.modifyReservation(
                UUID.randomUUID(), restaurantId, "Ana", "456", 4,
                LocalDateTime.of(2026, 3, 23, 14, 0)
        );

        assertNull(updated);
    }
}