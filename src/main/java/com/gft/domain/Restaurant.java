package com.gft.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class Restaurant {

    private final String id;
    private final String name;
    private final int capacity;
    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private final Set<DayOfWeek> closingDays;

    private Restaurant(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.capacity = builder.capacity;
        this.openingTime = builder.openingTime;
        this.closingTime = builder.closingTime;
        this.closingDays = builder.closingDays;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public LocalTime getOpeningTime() { return openingTime; }
    public LocalTime getClosingTime() { return closingTime; }
    public Set<DayOfWeek> getClosingDays() { return closingDays; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String name;
        private int capacity;
        private LocalTime openingTime;
        private LocalTime closingTime;
        private Set<DayOfWeek> closingDays;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder openingTime(LocalTime openingTime) {
            this.openingTime = openingTime;
            return this;
        }

        public Builder closingTime(LocalTime closingTime) {
            this.closingTime = closingTime;
            return this;
        }

        public Builder closingDays(Set<DayOfWeek> closingDays) {
            this.closingDays = closingDays;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}