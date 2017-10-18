package exception;

public class IllegalTicketUpdatingException extends Exception {
    public IllegalTicketUpdatingException(String message) {
        super(message);
    }

    public IllegalTicketUpdatingException() {
        super("Ticket cannot be changed");
    }
}
