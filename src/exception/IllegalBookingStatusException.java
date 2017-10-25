package exception;

public class IllegalBookingStatusException extends Exception {
    public IllegalBookingStatusException() {
        super("Illgal Booking Status.");
    }

    public IllegalBookingStatusException(String message) {
        super(message);
    }
}
