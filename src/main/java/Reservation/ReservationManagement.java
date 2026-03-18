package Reservation;

import Restaurant.*;

import java.util.HashMap;
import java.util.Map;

public class ReservationManagement {
    public static Map<Integer, Reservation> listOfReservations = new HashMap<>();

    public static boolean addReservation(Reservation reservation) {
        if (listOfReservations.containsKey(reservation.getReservationId())) {
            return false;
        }

        listOfReservations.put(reservation.getRestaurantId(), reservation);
        return true;
    }

    public static boolean deleteReservation(Reservation reservation) {
        if (listOfReservations.isEmpty())
            return false;

        listOfReservations.remove(reservation.getReservationId());

        return !listOfReservations.containsKey(reservation.getReservationId());
    }

    public static boolean modifyReservation(Reservation reservation) {
        return true;
    }
}
