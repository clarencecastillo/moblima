package exception;

public class HolidayNotFoundException extends RuntimeException {
    public HolidayNotFoundException() {
        super("This day is not a holiday.");
    }
}
