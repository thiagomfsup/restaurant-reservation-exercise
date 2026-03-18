import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    void shouldCalculateEndTimeTwoHoursAfterStartTime() {
        LocalDateTime startTime = LocalDateTime.of(2026, 3, 20, 19, 0);

        Reservation reservation = new Reservation(
                "restaurant-1",
                "John Doe",
                "600123123",
                4,
                startTime
        );

        assertThat(reservation.getStartTime()).isEqualTo(startTime);
        assertThat(reservation.getEndTime()).isEqualTo(startTime.plusHours(2));
    }

    @Test
    void shouldGenerateReservationIdWhenCreatingReservation() {
        Reservation reservation = new Reservation(
                "restaurant-1",
                "John Doe",
                "600123123",
                4,
                LocalDateTime.of(2026, 3, 20, 19, 0)
        );

        assertThat(reservation.getId()).isNotNull();
    }
}
