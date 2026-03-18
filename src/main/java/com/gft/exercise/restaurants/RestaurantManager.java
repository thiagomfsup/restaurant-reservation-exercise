package com.gft.exercise.restaurants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public interface RestaurantManager {
    Restaurant add(String name, int capacity, LocalTime open, LocalTime close, Set<LocalDate> closingDays);

    Restaurant getById(UUID uuid);

    Restaurant getById(String id);
}
