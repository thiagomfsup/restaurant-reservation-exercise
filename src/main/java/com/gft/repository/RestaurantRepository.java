package com.gft.repository;

import com.gft.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    void save(Restaurant restaurant);

    void delete(String id);

    Optional<Restaurant> findById(String id);

    List<Restaurant> findAll();
}