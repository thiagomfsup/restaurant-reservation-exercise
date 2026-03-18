package Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private UUID reservationId;
    private UUID restaurantId;
    private String customerName;
    private String phoneNumber;
    private int partySize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Reservation(UUID reservationId, UUID restaurantId, String customerName, String phoneNumber, int partySize, LocalDateTime startTime, LocalDateTime endTime) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.partySize = partySize;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getReservationId() { return reservationId; }
    public UUID getRestaurantId() { return restaurantId; }
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getPartySize() { return partySize; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
}