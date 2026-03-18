package exceptions;

public class OverlapException extends Exception {

    public OverlapException() {
        super("Reservation exceeds restaurant capacity due to overlapping reservations");
    }

    public OverlapException(String message) {
        super(message);
    }
}
