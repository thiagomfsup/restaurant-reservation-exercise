package Restaurant;

import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    private RestaurantManagement management;
    private Map<DayOfWeek, LocalTime[]> schedule;

    @BeforeEach
    void setUp() {
        management = new RestaurantManagement();
        schedule = new HashMap<>() {{
            put(DayOfWeek.MONDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.TUESDAY,   new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.THURSDAY,  new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(23, 0)});
            put(DayOfWeek.FRIDAY,    new LocalTime[]{LocalTime.of(13, 0), LocalTime.of(0, 0)});
            put(DayOfWeek.SATURDAY,  new LocalTime[]{LocalTime.of(12, 0), LocalTime.of(0, 0)});
        }};
    }

    @Test
    void shouldGenerateUniqueId() {
        Restaurant r1 = management.addRestaurant("Restaurante 1", 50, schedule, DayOfWeek.SUNDAY);
        Restaurant r2 = management.addRestaurant("Restaurante 2", 80, schedule, DayOfWeek.MONDAY);

        assertNotEquals(r1.getId(), r2.getId());
    }

    @Test
    void restaurantShouldExist() {
        Restaurant r = management.addRestaurant("Prueba", 125, schedule, DayOfWeek.SUNDAY);

        assertTrue(management.deleteRestaurant(r.getId()));
    }
}