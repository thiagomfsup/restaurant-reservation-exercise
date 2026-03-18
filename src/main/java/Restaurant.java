import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

@lombok.Builder
@Getter
public class Restaurant {
    private int id;
    private String name;
    private int capacity;
    private Map<DayOfWeek, LocalTime[]> schedule;
    private DayOfWeek closingDay;
}
