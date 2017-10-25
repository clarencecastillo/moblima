package model.cinema;

import config.TicketConfig;
import model.booking.TicketType;
import model.transaction.Priceable;

import java.util.List;

/**
 Represents a standard set of types of the cinema whose pricing rate is added to the price of the ticket.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public enum CinemaType implements Priceable {

    /**
     * The regular cinema type.
     */
    REGULAR("Regular"),

    /**
     * The Platinum cinema type.
     */
    PLATINUM("Platinum"),

    /**
     * The executive cinema type.
     */
    EXECUTIVE("Executive");

    /**
     * The name of the cinema type.
     */
    private String string;

    /**
     * Creates the cinema type with the given type.
     * @param string
     */
    CinemaType(String string) {
        this.string = string;
    }

    /**
     * Gets the pricing rate of this cinema type.
     * @return the pricing rate of this cinema type.
     */
    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    /**
     * Gets the list of available ticket type of this cinema type.
     * Each cinema type has limited ticket types set by the staff.
     * @return the list of available ticket type of this cinema type.
     */
    public List<TicketType> getTicketTypes() {
        return TicketConfig.getAvailableTicketTypes(this);
    }

    /**
     * Checks whether a ticket type is available in this cinema type.
     * @param type a ticket type to be checked
     * @return true if this ticket type is available in this cinema type.
     */
    public boolean isAvailable(TicketType type) {
        return TicketConfig.isAvailable(this, type);
    }

    /**
     * Gets the cinema type.
     * @return the cinema type.
     */
    @Override
    public String toString() {
        return string;
    }
}
