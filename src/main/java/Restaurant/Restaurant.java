package Restaurant;

import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
public class Restaurant {
    private UUID id;
    private String name;
    private int capacity;
    private Map<DayOfWeek, LocalTime[]> schedule;
    private DayOfWeek closingDay;
}
