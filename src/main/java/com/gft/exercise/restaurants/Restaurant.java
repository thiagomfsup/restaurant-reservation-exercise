package com.gft.exercise.restaurants;

import com.gft.exercise.reservations.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record Restaurant(UUID id, String name, int capacity, LocalTime open, LocalTime close,
                         Set<LocalDate> closingDays) {

    public Restaurant {
        if (name == null || open == null || close == null || closingDays == null) {
            throw new IllegalArgumentException("Input parameter cannot be null");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number");
        }

        if (close.isBefore(open)){
            throw new IllegalArgumentException("Close time must be after Open time");
        }

        // TODO more validations

        // ensure immutable
        closingDays = Set.copyOf(closingDays);
    }

    public void validate(Reservation newReservation) {
        final var dateTime = newReservation.dateTime();

        if (this.closingDays().contains(dateTime.toLocalDate())) {
            throw new IllegalArgumentException("Cannot make a reservation when the restaurant is closed");
        }

        if (dateTime.toLocalTime().isBefore(this.open())) {
            throw new IllegalArgumentException("Cannot make a reservation before restaurant's opening hour");
        }

        if (dateTime.toLocalTime().isAfter(this.close())) {
            throw new IllegalArgumentException("Cannot make a reservation after restaurant's closing hour");
        }
    }

    public static class Builder {
        private String name;
        private int capacity;
        private LocalTime open;
        private LocalTime close;
        private Set<LocalDate> closingDays = Set.of();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder openHour(LocalTime openHour) {
            this.open = openHour;
            return this;
        }

        public Builder closeHour(LocalTime closeHour) {
            this.close = closeHour;
            return this;
        }

        public Builder closingDays(Set<LocalDate> closingDays) {
            this.closingDays = closingDays;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(UUID.randomUUID(), this.name, this.capacity, this.open, this.close, this.closingDays);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}