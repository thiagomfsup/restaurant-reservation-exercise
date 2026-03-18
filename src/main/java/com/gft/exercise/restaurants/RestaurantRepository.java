package com.gft.exercise.restaurants;

import java.util.UUID;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Restaurant getById(UUID uuid);
    Restaurant delete(UUID uuid);
}
