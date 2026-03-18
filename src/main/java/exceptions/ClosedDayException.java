package exceptions;

public class ClosedDayException extends Exception {

    public ClosedDayException() {
        super("Cannot make a reservation on a day the restaurant is closed");
    }

    public ClosedDayException(String message) {
        super(message);
    }
}

