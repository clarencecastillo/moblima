package exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException() {
        super("This seat is not found.");
    }
}
