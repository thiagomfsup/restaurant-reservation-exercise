package com.gft.service;

import com.gft.domain.Restaurant;
import com.gft.domain.RestaurantNotFoundException;
import com.gft.repository.InMemoryRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestaurantServiceTest {

    private RestaurantService service;

    @BeforeEach
    void setUp() {
        service = new RestaurantService(new InMemoryRestaurantRepository());
    }

    @Test
    void shouldAddRestaurant() {
        Restaurant r = buildRestaurant("1", "Paulino", 50);

        service.addRestaurant(r);

        assertThat(service.findById("1")).isPresent();
    }

    @Test
    void shouldDeleteRestaurant() {
        service.addRestaurant(buildRestaurant("1", "Paulino", 50));

        service.deleteRestaurant("1");

        assertThat(service.findById("1")).isEmpty();
    }

    @Test
    void shouldUpdateRestaurant() {
        service.addRestaurant(buildRestaurant("1", "Paulino", 50));

        Restaurant updated = buildRestaurant("1", "Paulino Renovado", 80);
        service.updateRestaurant(updated);

        assertThat(service.findById("1").get().getName()).isEqualTo("Paulino Renovado");
    }

    @Test
    void shouldThrowWhenAddingNullRestaurant() {
        assertThatThrownBy(() -> service.addRestaurant(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentRestaurant() {
        assertThatThrownBy(() -> service.deleteRestaurant("no-existe"))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentRestaurant() {
        assertThatThrownBy(() -> service.updateRestaurant(buildRestaurant("no-existe", "Test", 10)))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

    @Test
    void shouldReturnEmptyWhenRestaurantNotFound() {
        assertThat(service.findById("no-existe")).isEmpty();
    }

    @Test
    void shouldThrowWhenUpdatingNullRestaurant() {
        assertThatThrownBy(() -> service.updateRestaurant(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldFindAllRestaurants() {
        service.addRestaurant(buildRestaurant("1", "Paulino", 50));
        service.addRestaurant(buildRestaurant("2", "El Corzo", 30));

        assertThat(service.findAll()).hasSize(2);
    }

    private Restaurant buildRestaurant(String id, String name, int capacity) {
        return Restaurant.builder()
                .id(id)
                .name(name)
                .capacity(capacity)
                .openingTime(LocalTime.of(13, 0))
                .closingTime(LocalTime.of(23, 0))
                .closingDays(Set.of(DayOfWeek.MONDAY))
                .build();
    }
}