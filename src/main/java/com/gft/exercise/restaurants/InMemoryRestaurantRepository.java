package com.gft.exercise.restaurants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryRestaurantRepository implements RestaurantRepository {

    private final Map<UUID, Restaurant> restaurantMap = new HashMap<>();

    @Override
    public Restaurant save(Restaurant restaurant) {
        restaurantMap.put(restaurant.id(), restaurant);

        return restaurant;
    }

    @Override
    public Restaurant getById(UUID uuid) {
        return restaurantMap.get(uuid);
    }

    @Override
    public Restaurant delete(UUID uuid) {
        return restaurantMap.remove(uuid);
    }
}
