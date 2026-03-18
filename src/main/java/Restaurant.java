import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Restaurant {

    private int id;
    private String name;
    private int capacity;
    private LocalTime openingHours;// OPENING HOURS
    private DayOfWeek[] closingDays;

    public Restaurant() {
    }

    public Restaurant(int id, String name, int capacity, LocalTime openingHours, DayOfWeek[] closingDays) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.openingHours = openingHours;
        this.closingDays = closingDays;
    }


    @Override
    public int hashCode() {
        return id*31;
    }

    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return  false;

        Restaurant restaurant = (Restaurant) o;
        return this.id == restaurant.id;

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    public DayOfWeek[] getClosingDays() {
        return closingDays;
    }

    public void setClosingDays(DayOfWeek[] closingDays) {
        this.closingDays = closingDays;
    }
}