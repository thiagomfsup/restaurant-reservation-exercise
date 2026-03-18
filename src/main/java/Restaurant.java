public class Restaurant {

    private int id;
    private String name;
    private int capacity;
    // OPENING HOURS
    // OPENING DAYS

    public Restaurant(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
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


}

enum Day {
    MONDAY(),
    TUESDAY(),
    WEDNESDAY(),
    THURSDAY(),
    FRIDAY(),
    SATURDAY(),
    SUNDAY();

    Day() {

    }
}
