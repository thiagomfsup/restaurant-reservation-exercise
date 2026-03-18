import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantManagerTest {

    private static RestaurantManager restaurantManager;

    @Test
    void addRestaurant() {
        restaurantManager = new RestaurantManager();
        Restaurant restaurant = new Restaurant(1, "Nombre_Restaurante", 100, LocalTime.of(9,0,0), new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY});

        restaurantManager.addRestaurant(restaurant);

        assertThat(restaurantManager.getRestaurants())
                .contains(restaurant);

    }

    @Test
    void removeRestaurant() {
        restaurantManager = new RestaurantManager();
        Restaurant restaurant = new Restaurant(1, "Nombre_Restaurante", 100, LocalTime.of(9,0,0), new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY});

        restaurantManager.addRestaurant(restaurant);
        restaurantManager.deleteRestaurant(restaurant);

        assertThat(restaurantManager.getRestaurants())
                .doesNotContain(restaurant);

    }

    @Test
    void editRestaurant() {
        restaurantManager = new RestaurantManager();
        Restaurant restaurant = new Restaurant(1, "Nombre_Restaurante", 100, LocalTime.of(9,0,0), new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.SUNDAY});
        restaurantManager.addRestaurant(restaurant);

        // Modificamos
        restaurant.setName("RestauranteModificado");
        restaurantManager.modifyRestaurant(restaurant);

        Set<Restaurant> restaurants = restaurantManager.getRestaurants();

        assertThat(restaurants)
                .extracting(Restaurant::getName)
                .contains("RestauranteModificado")
                .doesNotContain("Restaurante1");
    }

}
