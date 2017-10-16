package manager.exception;

public class InvalidTicketStatusException extends Exception {
    public InvalidTicketStatusException(String message) {
        super(message);
    }

    public InvalidTicketStatusException() {
        super("Ticket is not valid");
    }
}
