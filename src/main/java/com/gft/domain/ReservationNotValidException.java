package com.gft.domain;

public class ReservationNotValidException extends RuntimeException {

    public ReservationNotValidException(String message) {
        super(message);
    }
}