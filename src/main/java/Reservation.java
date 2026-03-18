import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private final UUID id;
    private final String restaurantId;
    private final String customerName;
    private final String customerPhoneNumber;
    private final int partySize;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Reservation(String restaurantId, String customerName, String customerPhoneNumber, int partySize, LocalDateTime startTime) {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID is required");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customerPhoneNumber == null || customerPhoneNumber.isBlank()) {
            throw new IllegalArgumentException("Customer phone number is required");
        }
        if (partySize <= 0) {
            throw new IllegalArgumentException("Party size must be greater than zero");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Start time is required");
        }

        this.id = UUID.randomUUID();
        this.restaurantId = restaurantId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.partySize = partySize;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(2);
    }

    public UUID getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public int getPartySize() {
        return partySize;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
