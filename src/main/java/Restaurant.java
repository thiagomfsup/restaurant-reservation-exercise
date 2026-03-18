import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;

public class Restaurant {

    private String id;
    private String name;
    private int maxCapacity;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Set<DayOfWeek> closingDays;

    public Restaurant(
            String id,
            String name,
            LocalTime openingTime,
            int maxCapacity,
            LocalTime closingTime,
            Set<DayOfWeek> closingDays
    ) {


        //Validations if a restaurant is created
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

        //If closingDays is null, it will be initialized as an empty set, otherwise it will be copied, to prevent null pointer exceptions
        this.closingDays = closingDays == null ? Collections.emptySet() : Set.copyOf(closingDays);
    }


    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
}
