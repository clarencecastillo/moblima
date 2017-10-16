package manager.exception;

public class IllegalTicketRemovingException extends Exception {
    public IllegalTicketRemovingException(String message) {
        super(message);
    }

    public IllegalTicketRemovingException() {
        super("Ticket can not be removed");
    }
}
