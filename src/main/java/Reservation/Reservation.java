package Reservation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Reservation {
    private UUID reservationId;
    private int restaurantId;
    private String customerName;
    private int phoneNumber;
    private int reservationSize;
    private LocalDateTime reservationDate;
}
