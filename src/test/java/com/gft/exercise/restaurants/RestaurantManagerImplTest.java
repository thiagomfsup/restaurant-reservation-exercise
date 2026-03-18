package com.gft.exercise.restaurants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestaurantManagerImplTest {

    private RestaurantManager restaurantManager;

    @BeforeEach
    void setUp() {
        restaurantManager = new RestaurantManagerImpl();
    }

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
    void shouldRegisterAValidRestaurant() {
        Restaurant aName = restaurantManager.add("A Name", 10, LocalTime.now(), LocalTime.now().plusHours(8), Set.of());

        assertThat(aName).isNotNull();

        assertThat(restaurantManager.getById(aName.id())).isNotNull();
    }
}