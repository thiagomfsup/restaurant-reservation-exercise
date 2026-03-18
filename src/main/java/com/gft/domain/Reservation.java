package com.gft.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private final String id;
    private final String restaurantId;
    private final String customerName;
    private final String customerPhone;
    private final int partySize;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private Reservation(Builder builder) {
        this.id = builder.id;
        this.restaurantId = builder.restaurantId;
        this.customerName = builder.customerName;
        this.customerPhone = builder.customerPhone;
        this.partySize = builder.partySize;
        this.startTime = builder.startTime;
        this.endTime = builder.startTime.plusHours(2);
    }

    public String getId() { return id; }
    public String getRestaurantId() { return restaurantId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public int getPartySize() { return partySize; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id = UUID.randomUUID().toString();
        private String restaurantId;
        private String customerName;
        private String customerPhone;
        private int partySize;
        private LocalDateTime startTime;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder restaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder customerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
            return this;
        }

        public Builder partySize(int partySize) {
            this.partySize = partySize;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }
    }
}