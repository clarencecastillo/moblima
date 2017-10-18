package exception;

public class UnavailableBookingSeatException extends Exception {
    public UnavailableBookingSeatException() {
        super("Seat is unavailable");
    }
}
