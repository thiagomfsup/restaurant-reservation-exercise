package com.gft.exercise.reservations;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReservationManager {
    Reservation add(UUID restaurantId, String customerName, String customerPhone, int partySize, LocalDateTime dateTime);
}
