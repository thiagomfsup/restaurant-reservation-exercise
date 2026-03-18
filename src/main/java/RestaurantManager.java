import java.util.HashSet;
import java.util.Set;

public class RestaurantManager {
    private Set<Restaurant> restaurantsSet;

    public RestaurantManager() {
        this.restaurantsSet = new HashSet<>();
    }

    public Set<Restaurant> getRestaurants() {
        return  this.restaurantsSet;
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurantsSet.add(restaurant);
    }

    public void deleteRestaurant(Restaurant restaurant) {
        this.restaurantsSet.remove(restaurant);
    }

    public void modifyRestaurant(Restaurant restaurant) {
        this.deleteRestaurant(restaurant);
        this.addRestaurant(restaurant);
    }




}
