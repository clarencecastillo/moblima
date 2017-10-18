package exception;

public class IllegalBookingChangeException extends Exception {
    public IllegalBookingChangeException() {
        super("Booking cannot be changed");
    }

    public IllegalBookingChangeException(String message) {
        super(message);
    }
}
