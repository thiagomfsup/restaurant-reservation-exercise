package com.gft.exercise.restaurants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RestaurantManagerImpl implements RestaurantManager {

    private static final Map<UUID, Restaurant> restaurants = new HashMap<>();

    @Override
    public Restaurant add(String name, int capacity, LocalTime open, LocalTime close, Set<LocalDate> closingDays) {
        if (name == null || open == null || close == null || closingDays == null) {
            throw new IllegalArgumentException("Input parameter cannot be null");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number");
        }

        // TODO more validations

        final var restaurant = Restaurant.builder()
                .name(name)
                .capacity(capacity)
                .openHour(open)
                .closeHour(close)
                .closingDays(closingDays)
                .build();

        restaurants.put(restaurant.id(), restaurant);

        return restaurant;
    }

    @Override
    public Restaurant getById(UUID uuid) {
        return restaurants.get(uuid);
    }

    @Override
    public Restaurant getById(String id) {
        return restaurants.get(UUID.fromString(id));
    }
}
