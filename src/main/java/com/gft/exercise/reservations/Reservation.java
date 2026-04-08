package com.gft.exercise.reservations;

import java.time.LocalDateTime;
import java.util.UUID;

public record Reservation(UUID id, UUID restaurantId, Customer customer, int partySize, LocalDateTime dateTime, int duration) {

    public Reservation {
        if (restaurantId == null)
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        duration = 2; // 2h by default
    }

    public boolean overlap(Reservation other) {
        final var thisStart = this.dateTime.toLocalTime();
        final var thisEnd = this.dateTime.toLocalTime().plusHours(duration);
        final var otherStart = other.dateTime.toLocalTime();
        final var otherEnd = other.dateTime.toLocalTime().plusHours(duration);

        return otherStart.isBefore(thisEnd) && otherEnd.isAfter(thisStart);
    }

    public record Customer(String name, String phoneNumber) {}

    public static Reservation.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID restaurantId;
        private String customerName;
        private String customePhoneNumber;
        private int partySize;
        private LocalDateTime dateTime;

        private Builder(){}

        public Builder setRestaurantId(UUID restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder setCustomePhoneNumber(String customePhoneNumber) {
            this.customePhoneNumber = customePhoneNumber;
            return this;
        }

        public Builder setPartySize(int partySize) {
            this.partySize = partySize;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Reservation build() {
            return new Reservation(UUID.randomUUID(), restaurantId, new Customer(customerName, customePhoneNumber), partySize, dateTime, 2);
        }

    }
}
