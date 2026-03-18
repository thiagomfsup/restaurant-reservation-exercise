import java.util.HashMap;
import java.util.Map;

public class RestaurantManagement {
    private static final Map<Integer, Restaurant> listOfRestaurants = new HashMap<>();

    public static Restaurant addRestaurant(Restaurant restaurant) {
        if (listOfRestaurants.containsKey(restaurant.getId())) {
            return null;
        }

        listOfRestaurants.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public static boolean deleteRestaurant(Restaurant restaurant) {
        if (listOfRestaurants.isEmpty())
            return false;

        listOfRestaurants.remove(restaurant.getId());

        return !listOfRestaurants.containsKey(restaurant.getId());
    }
}
