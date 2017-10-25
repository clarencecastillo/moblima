package exception;

public class UnavailableTicketTypeException extends RuntimeException {
    public UnavailableTicketTypeException() {
        super("Ticket type not available");
    }
}
