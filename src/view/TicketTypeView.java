package view;

import model.booking.TicketType;
import view.ui.View;

/**
 * This view displays the user interface for the user view current selected ticket types.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class TicketTypeView extends View {

    public TicketTypeView(TicketType ticketType, double price, int quantity) {
        setTitle(ticketType.toString());
        setContent(
                "Price: " + String.format("$%.2f", price),
                "Quantity: " + quantity
        );
    }
}
