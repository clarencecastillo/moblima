import java.util.UUID;
import manager.TicketManager;
import model.booking.Ticket;
import model.booking.TicketType;

public class Main {

    public static void main(String[] args) {
        TicketManager ticketManager = TicketManager.getInstance();
        Ticket ticket = ticketManager.createTicket(UUID.randomUUID(), null, TicketType.STUDENT);

    }
}
