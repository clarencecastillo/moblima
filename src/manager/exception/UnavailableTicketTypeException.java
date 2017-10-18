package manager.exception;

public class UnavailableTicketTypeException extends Exception {
    public UnavailableTicketTypeException() {
        super("Ticket type not available");
    }
}
