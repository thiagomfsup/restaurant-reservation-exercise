package com.gft.exercise.restaurants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RestaurantManagerImpl implements RestaurantManager {

    private final RestaurantRepository repository;

    public RestaurantManagerImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant add(String name, int capacity, LocalTime open, LocalTime close, Set<LocalDate> closingDays) {
        final var restaurant = Restaurant.builder()
                .name(name)
                .capacity(capacity)
                .openHour(open)
                .closeHour(close)
                .closingDays(closingDays)
                .build();

        repository.save(restaurant);

        return restaurant;
    }

    @Override
    public Restaurant getById(UUID uuid) {
        return repository.getById(uuid);
    }

    @Override
    public Restaurant getById(String id) {
        return repository.getById(UUID.fromString(id));
    }
}
