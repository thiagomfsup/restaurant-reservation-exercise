package Reservation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Reservation {
    private UUID reservationId;
    private UUID restaurantId;
    private String customerName;
    private String phoneNumber;
    private int partySize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
