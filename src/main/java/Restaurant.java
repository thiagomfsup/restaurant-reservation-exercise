import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import exceptions.ClosedDayException;
import exceptions.OutsideOpeningHoursException;
import exceptions.OverlapException;

public class Restaurant {

    private String id;
    private String name;
    private int maxCapacity;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Set<DayOfWeek> closingDays;
    private final List<Reservation> reservations;

    public Restaurant(
            String id,
            String name,
            LocalTime openingTime,
            int maxCapacity,
            LocalTime closingTime,
            Set<DayOfWeek> closingDays
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Restaurant id is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name is required");
        }
        if (openingTime == null || closingTime == null) {
            throw new IllegalArgumentException("Opening and closing times are required");
        }
        if (!closingTime.isAfter(openingTime)) {
            throw new IllegalArgumentException("Closing time must be after opening time");
        }
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero");
        }

        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.closingDays = closingDays == null ? Collections.emptySet() : Set.copyOf(closingDays);
        this.reservations = new ArrayList<>();
    }

    // ---- Reservation logic ----

    public void reserve(Reservation reservation) throws OverlapException, ClosedDayException, OutsideOpeningHoursException {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation is required");
        }
        if (reservation.getPartySize() <= 0) {
            throw new IllegalArgumentException("Party size must be greater than zero");
        }

        // Validate closing day
        DayOfWeek reservationDay = reservation.getStartTime().getDayOfWeek();
        if (closingDays.contains(reservationDay)) {
            throw new ClosedDayException("Restaurant is closed on " + reservationDay);
        }

        // Validate opening hours
        LocalTime startLocal = reservation.getStartTime().toLocalTime();
        LocalTime endLocal = reservation.getEndTime().toLocalTime();
        if (startLocal.isBefore(openingTime)) {
            throw new OutsideOpeningHoursException("Reservation cannot start before opening time (" + openingTime + ")");
        }
        if (endLocal.isAfter(closingTime)) {
            throw new OutsideOpeningHoursException("Reservation cannot end after closing time (" + closingTime + ")");
        }

        // Validate capacity
        if (reservation.getPartySize() > maxCapacity) {
            throw new OverlapException("Party size (" + reservation.getPartySize() + ") exceeds max capacity (" + maxCapacity + ")");
        }

        int overlappingPeople = 0;
        for (Reservation existing : reservations) {
            if (isOverlapping(existing, reservation)) {
                overlappingPeople += existing.getPartySize();
            }
        }

        if (overlappingPeople + reservation.getPartySize() > maxCapacity) {
            throw new OverlapException("Total overlapping people (" + (overlappingPeople + reservation.getPartySize()) + ") exceeds capacity (" + maxCapacity + ")");
        }

        reservations.add(reservation);
    }

    public boolean cancelReservation(UUID reservationId) {
        return reservations.removeIf(r -> r.getId().equals(reservationId));
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    // ---- Helpers ----

    private boolean isOverlapping(Reservation a, Reservation b) {
        return a.getStartTime().isBefore(b.getEndTime())
                && a.getEndTime().isAfter(b.getStartTime());
    }

    // ---- Getters ----

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public Set<DayOfWeek> getClosingDays() {
        return closingDays;
    }

    // ---- Setters for modification ----

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name is required");
        }
        this.name = name;
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than zero");
        }
        this.maxCapacity = maxCapacity;
    }

    public void setOpeningTime(LocalTime openingTime) {
        if (openingTime == null) {
            throw new IllegalArgumentException("Opening time is required");
        }
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        if (closingTime == null) {
            throw new IllegalArgumentException("Closing time is required");
        }
        this.closingTime = closingTime;
    }

    public void setClosingDays(Set<DayOfWeek> closingDays) {
        this.closingDays = closingDays == null ? Collections.emptySet() : Set.copyOf(closingDays);
    }
}
