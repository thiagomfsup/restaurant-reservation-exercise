import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class RestaurantTest {

    @Test
    void shouldCreateRestaurantWithValidData() {
        Restaurant restaurant = new Restaurant(
                "rest-1",
                "La Plaza",
                LocalTime.of(12, 0),
                50,
                LocalTime.of(23, 0),
                Set.of(DayOfWeek.MONDAY)
        );

        assertThat(restaurant.getId()).isEqualTo("rest-1");
        assertThat(restaurant.getName()).isEqualTo("La Plaza");
        assertThat(restaurant.getMaxCapacity()).isEqualTo(50);
        assertThat(restaurant.getOpeningTime()).isEqualTo(LocalTime.of(12, 0));
        assertThat(restaurant.getClosingTime()).isEqualTo(LocalTime.of(23, 0));
        assertThat(restaurant.getClosingDays()).containsExactly(DayOfWeek.MONDAY);
    }

    @Test
    void shouldRejectNullOrBlankId() {
        assertThatThrownBy(() -> new Restaurant(
                null,
                "La Plaza",
                LocalTime.of(12, 0),
                50,
                LocalTime.of(23, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Restaurant(
                "   ",
                "La Plaza",
                LocalTime.of(12, 0),
                50,
                LocalTime.of(23, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNullOrBlankName() {
        assertThatThrownBy(() -> new Restaurant(
                "rest-1",
                null,
                LocalTime.of(12, 0),
                50,
                LocalTime.of(23, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Restaurant(
                "rest-1",
                "   ",
                LocalTime.of(12, 0),
                50,
                LocalTime.of(23, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNonPositiveMaxCapacity() {
        assertThatThrownBy(() -> new Restaurant(
                "rest-1",
                "La Plaza",
                LocalTime.of(12, 0),
                0,
                LocalTime.of(23, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Restaurant(
                "rest-1",
                "La Plaza",
                LocalTime.of(12, 0),
                -5,
                LocalTime.of(23, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectInvalidOpeningClosingTimes() {
        assertThatThrownBy(() -> new Restaurant(
                "rest-1",
                "La Plaza",
                LocalTime.of(12, 0),
                50,
                LocalTime.of(12, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Restaurant(
                "rest-1",
                "La Plaza",
                LocalTime.of(13, 0),
                50,
                LocalTime.of(12, 0),
                Set.of()
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldUseEmptyClosingDaysWhenNullProvided() {
        Restaurant restaurant = new Restaurant(
                "rest-1",
                "La Plaza",
                LocalTime.of(12, 0),
                50,
                LocalTime.of(23, 0),
                null
        );

        assertThat(restaurant.getClosingDays()).isEmpty();
    }
}
