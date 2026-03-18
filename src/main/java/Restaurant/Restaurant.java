package Restaurant;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

public class Restaurant {
    private UUID id;
    private String name;
    private int capacity;
    private Map<DayOfWeek, LocalTime[]> schedule;
    private DayOfWeek closingDay;

    public Restaurant(UUID id, String name, int capacity, Map<DayOfWeek, LocalTime[]> schedule, DayOfWeek closingDay) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.schedule = schedule;
        this.closingDay = closingDay;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public Map<DayOfWeek, LocalTime[]> getSchedule() { return schedule; }
    public DayOfWeek getClosingDay() { return closingDay; }
}