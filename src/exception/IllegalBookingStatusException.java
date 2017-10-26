package exception;

public class IllegalBookingStatusException extends RuntimeException {
    public IllegalBookingStatusException() {
        super("Illgal Booking Status.");
    }

    public IllegalBookingStatusException(String message) {
        super(message);
    }
}
