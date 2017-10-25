package exception;

public class SeatNotFoundException extends Exception {
    public SeatNotFoundException() {
        super("This seat is not found.");
    }
}
