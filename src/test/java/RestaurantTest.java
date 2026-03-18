import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant firstRestaurant = Restaurant.builder()
            .id(1)
            .name("Prueba")
            .capacity(125)
            .schedule(new HashMap<>() {{
                put(DayOfWeek.MONDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.TUESDAY,   new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.THURSDAY,  new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.FRIDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(0, 0)});
                put(DayOfWeek.SATURDAY,  new LocalTime[]{LocalTime.of(12, 0), LocalTime.of(0, 0)});
            }})
            .closingDay(DayOfWeek.SUNDAY)
            .build();

    private Restaurant secondRestaurant = Restaurant.builder()
            .id(1)
            .name("Prueba")
            .capacity(125)
            .schedule(new HashMap<>() {{
                put(DayOfWeek.MONDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.TUESDAY,   new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.THURSDAY,  new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
                put(DayOfWeek.FRIDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(0, 0)});
                put(DayOfWeek.SATURDAY,  new LocalTime[]{LocalTime.of(12, 0), LocalTime.of(0, 0)});
            }})
            .closingDay(DayOfWeek.SUNDAY)
            .build();

    @Test
    public void idShouldBeUnique() {
        RestaurantManagement.addRestaurant(firstRestaurant);
        assertNotNull(RestaurantManagement.addRestaurant(secondRestaurant));
    }

    @Test
    public void restaurantShouldBeDeleted() {
        RestaurantManagement.addRestaurant(firstRestaurant);
        assertTrue(RestaurantManagement.deleteRestaurant(firstRestaurant));
    }
}