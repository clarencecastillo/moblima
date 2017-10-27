package view;

import model.booking.TicketType;
import view.ui.View;

public class TicketTypeView extends View {

    public TicketTypeView(TicketType ticketType, double price, int quantity) {
        setTitle(ticketType.toString());
        setContent(
                "Price: " + String.format("$%.2f", price),
                "Quantity: " + quantity
        );
    }
}
