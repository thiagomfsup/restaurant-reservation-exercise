package Restaurant;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestaurantManagement {
    private final Map<UUID, Restaurant> restaurants = new HashMap<>();

    public Restaurant addRestaurant(String name, int capacity, Map<DayOfWeek, LocalTime[]> schedule, DayOfWeek closingDay) {
        Restaurant restaurant = Restaurant.builder()
                .id(UUID.randomUUID())
                .name(name)
                .capacity(capacity)
                .schedule(schedule)
                .closingDay(closingDay)
                .build();

        restaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public boolean deleteRestaurant(UUID id) {
        if (!restaurants.containsKey(id)) {
            return false;
        }
        restaurants.remove(id);
        return true;
    }

    public Restaurant getRestaurant(UUID id) {
        return restaurants.get(id);
    }

    public Map<UUID, Restaurant> getAllRestaurants() {
        return restaurants;
    }
}
