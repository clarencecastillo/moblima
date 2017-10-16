package manager.exception;

public class IllegalShowtimeBookingException extends Exception {
    public IllegalShowtimeBookingException(String message) {
        super(message);
    }

    public IllegalShowtimeBookingException() {
        super("Showtime is not open for booking yet.");

    }
}
