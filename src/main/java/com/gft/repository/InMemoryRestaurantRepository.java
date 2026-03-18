package com.gft.repository;

import com.gft.domain.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRestaurantRepository implements RestaurantRepository {

    private final Map<String, Restaurant> storage = new HashMap<>();

    @Override
    public void save(Restaurant restaurant) {
        storage.put(restaurant.getId(), restaurant);
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
    }

    @Override
    public Optional<Restaurant> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Restaurant> findAll() {
        return new ArrayList<>(storage.values());
    }
}