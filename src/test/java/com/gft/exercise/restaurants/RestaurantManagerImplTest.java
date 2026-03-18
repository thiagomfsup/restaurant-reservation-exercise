package com.gft.exercise.restaurants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class RestaurantManagerImplTest {

    @Spy
    private RestaurantRepository restaurantRepository = new InMemoryRestaurantRepository();

    @InjectMocks
    private RestaurantManagerImpl restaurantManager;

    @Test
    void shouldNotAcceptNullName() {
        assertThatThrownBy(() -> restaurantManager.add(null, 10, LocalTime.now(), LocalTime.now().plusHours(8), Set.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAcceptNullOpenTime() {
        assertThatThrownBy(() -> restaurantManager.add("A Name", 10, null, LocalTime.now().plusHours(8), Set.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ... more validation here

    @Test
    void shouldNotAcceptNegativeCapacity() {
        assertThatThrownBy(() -> restaurantManager.add("A Name", -10, LocalTime.now(), LocalTime.now().plusHours(8), Set.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAcceptCloseTimeBeforeOpenTime() {
        assertThatThrownBy(() -> restaurantManager.add("A Name", 10, LocalTime.now(), LocalTime.now().minusHours(1), Set.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRegisterAValidRestaurant() {
        Restaurant aName = restaurantManager.add("A Name", 10, LocalTime.now(), LocalTime.now().plusHours(8), Set.of());

        assertThat(aName).isNotNull();

        assertThat(restaurantManager.getById(aName.id())).isNotNull();
    }
}