package com.gft.service;

import com.gft.domain.Restaurant;
import com.gft.domain.RestaurantNotFoundException;
import com.gft.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

public class RestaurantService {

    private final RestaurantRepository repository;

    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public void addRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("El restaurante no puede ser null");
        }
        repository.save(restaurant);
    }

    public void deleteRestaurant(String id) {
        repository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        repository.delete(id);
    }

    public void updateRestaurant(Restaurant updated) {
        if (updated == null) {
            throw new IllegalArgumentException("El restaurante no puede ser null");
        }
        repository.findById(updated.getId())
                .orElseThrow(() -> new RestaurantNotFoundException(updated.getId()));
        repository.save(updated);
    }

    public Optional<Restaurant> findById(String id) {
        return repository.findById(id);
    }
}