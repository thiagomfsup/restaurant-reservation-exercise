package Reservation;

import Restaurant.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReservationManagement {
    private final Map<UUID, Reservation> reservations = new HashMap<>();
    private final RestaurantManagement restaurantManagement;

    public ReservationManagement(RestaurantManagement restaurantManagement) {
        this.restaurantManagement = restaurantManagement;
    }

    public Reservation addReservation(UUID restaurantId, String customerName, String phoneNumber, int partySize, LocalDateTime startTime) {
        boolean valid = validate(restaurantId, partySize, startTime, null);

        if (!valid)
            return null;

        LocalDateTime endTime = startTime.plusHours(2);
        Reservation reservation = Reservation.builder()
                .reservationId(UUID.randomUUID())
                .restaurantId(restaurantId)
                .customerName(customerName)
                .phoneNumber(phoneNumber)
                .partySize(partySize)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        reservations.put(reservation.getReservationId(), reservation);
        return reservation;
    }

    public boolean deleteReservation(UUID reservationId) {
        if (!reservations.containsKey(reservationId))
            return false;

        reservations.remove(reservationId);
        return true;
    }

    public Reservation modifyReservation(UUID reservationId, UUID restaurantId, String customerName, String phoneNumber, int partySize, LocalDateTime startTime) {
        if (!reservations.containsKey(reservationId))
            return null;

        validate(restaurantId, partySize, startTime, reservationId);

        LocalDateTime endTime = startTime.plusHours(2);
        Reservation updated = Reservation.builder()
                .reservationId(reservationId)
                .restaurantId(restaurantId)
                .customerName(customerName)
                .phoneNumber(phoneNumber)
                .partySize(partySize)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        reservations.put(reservationId, updated);
        return updated;
    }

    private boolean validate(UUID restaurantId, int partySize, LocalDateTime startTime, UUID excludeReservationId) {
        Restaurant restaurant = restaurantManagement.getRestaurant(restaurantId);

        if (restaurant == null)
            return false;

        DayOfWeek day = startTime.getDayOfWeek();

        if (day.equals(restaurant.getClosingDay()))
            return false;

        LocalTime[] hours = restaurant.getSchedule().get(day);
        if (hours == null)
            return false;

        LocalTime openingTime = hours[0];
        LocalTime closingTime = hours[1];
        LocalTime start = startTime.toLocalTime();
        LocalTime end = start.plusHours(2);

        if (start.isBefore(openingTime))
             return false;

        if (!closingTime.equals(LocalTime.MIDNIGHT) && end.isAfter(closingTime))
            return false;

        LocalDateTime endTime = startTime.plusHours(2);
        int overlappingPartySize = getOverlappingPartySize(restaurantId, startTime, endTime, excludeReservationId);

        return overlappingPartySize + partySize <= restaurant.getCapacity();
    }

    private int getOverlappingPartySize(UUID restaurantId, LocalDateTime start, LocalDateTime end, UUID excludeReservationId) {
        return reservations.values().stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .filter(r -> !r.getReservationId().equals(excludeReservationId))
                .filter(r -> r.getStartTime().isBefore(end) && r.getEndTime().isAfter(start))
                .mapToInt(Reservation::getPartySize)
                .sum();
    }
}
