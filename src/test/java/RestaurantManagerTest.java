import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantManagerTest {

    private static RestaurantManager restaurantManager;

    @Test
    void addRestaurant() {
        restaurantManager = new RestaurantManager();
        Restaurant restaurant = new Restaurant(1,"Restaurante1", 100);

        restaurantManager.addRestaurant(restaurant);

        assertThat(restaurantManager.getRestaurants())
                .contains(restaurant);

    }

    @Test
    void removeRestaurant() {
        restaurantManager = new RestaurantManager();
        Restaurant restaurant = new Restaurant(1,"Restaurante1", 100);

        restaurantManager.addRestaurant(restaurant);
        restaurantManager.deleteRestaurant(restaurant);

        assertThat(restaurantManager.getRestaurants())
                .doesNotContain(restaurant);

    }

}
