import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTest {

    @Test
    void testRestaurantAttributes(){
        Restaurant restaurant = new Restaurant(1, "Nombre_Restaurante", 100, LocalTime.of(9,0,0), new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY});
        assertThat(
                restaurant.getId()
        ).isNotNull();
        assertThat(
                restaurant.getName()
        ).isNotNull();
        assertThat(
                restaurant.getCapacity()
        ).isNotNull();
        assertThat(
                restaurant.getOpeningHours()
        ).isNotNull();
        assertThat(
                restaurant.getClosingDays()
        ).isNotNull();
    }
}
