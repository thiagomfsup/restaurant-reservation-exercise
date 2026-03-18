package com.gft.domain;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String reservationId) {
        super("No existe ninguna reserva con id: " + reservationId);
    }
}