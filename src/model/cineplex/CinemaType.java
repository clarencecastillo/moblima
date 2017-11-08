package model.cineplex;

import config.TicketConfig;
import model.booking.TicketType;
import model.transaction.Priceable;

import java.util.List;

/**
 * Represents a standard set of types of the cineplex whose pricing rate is added to the price of the ticket.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public enum CinemaType implements Priceable {

    /**
     * The regular cineplex type.
     */
    REGULAR("Regular"),

    /**
     * The Platinum cineplex type.
     */
    PLATINUM("Platinum"),

    /**
     * The executive cineplex type.
     */
    EXECUTIVE("Executive");

    /**
     * The name of the cineplex type.
     */
    private String string;

    /**
     * Creates the cineplex type with the given type.
     *
     * @param string The name of this cineplex type.
     */
    CinemaType(String string) {
        this.string = string;
    }

    /**
     * Gets the pricing rate of this cineplex type.
     *
     * @return the pricing rate of this cineplex type.
     */
    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    /**
     * Gets the list of available ticket type of this cineplex type.
     * Each cineplex type has limited ticket types set by the staff.
     *
     * @return the list of available ticket type of this cineplex type.
     */
    public List<TicketType> getTicketTypes() {
        return TicketConfig.getAvailableTicketTypes(this);
    }

    /**
     * Checks whether a ticket type is available in this cineplex type.
     *
     * @param type a ticket type to be checked
     * @return true if this ticket type is available in this cineplex type.
     */
    public boolean isAvailable(TicketType type) {
        return TicketConfig.isAvailable(this, type);
    }

    /**
     * Gets the cineplex type.
     *
     * @return the cineplex type.
     */
    @Override
    public String toString() {
        return string;
    }
}
