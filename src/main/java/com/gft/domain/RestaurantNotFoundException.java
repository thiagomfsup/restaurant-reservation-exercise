package com.gft.domain;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String restaurantId) {
        super("No existe ningún restaurante con id: " + restaurantId);
    }
}