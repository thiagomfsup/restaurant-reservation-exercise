import java.time.LocalDateTime;
import java.util.UUID;

class Reservation {
    private final UUID id;
    private final String restaurantId;
    private final String customerName;
    private final String customerPhoneNumber;
    private final int partySize;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    Reservation(String restaurantId, String customerName, String customerPhoneNumber, int partySize, LocalDateTime startTime) {
        this.id = UUID.randomUUID();
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.partySize = partySize;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(2);
    }

    UUID getId() {
        return id;
    }

    String getRestaurantId() {
        return restaurantId;
    }

    String getCustomerName() {
        return customerName;
    }

    String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    int getPartySize() {
        return partySize;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    LocalDateTime getEndTime() {
        return endTime;
    }
}
