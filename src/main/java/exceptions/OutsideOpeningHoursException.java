package exceptions;

public class OutsideOpeningHoursException extends Exception {

    public OutsideOpeningHoursException() {
        super("Reservation must be within restaurant opening hours");
    }

    public OutsideOpeningHoursException(String message) {
        super(message);
    }
}

