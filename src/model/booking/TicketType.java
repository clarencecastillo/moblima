package model.booking;

import config.TicketConfig;
import model.transaction.Priceable;

/**
 * Represents a standard set of ticket types whose pricing rate will be added to the ticket price.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum TicketType implements Priceable {

    /**
     * The student ticket type.
     */
    STUDENT("Student Ticket"),

    /**
     * The senior citizen ticket type.
     */
    SENIOR_CITIZEN("Senior Citizen Ticket"),

    /**
     * The standard ticket type.
     */
    STANDARD("Standard Ticket"),

    /**
     * The peak day ticket type.
     */
    PEAK("Fri - Sun, Eve of PH & PH Ticket");

    /**
     * The name of this ticket type.
     */
    private String name;

    /**
     * Creates a ticket type with s given ticket type name.
     *
     * @param string The name of this ticket type.
     */
    TicketType(String string) {
        this.name = string;
    }

    /**
     * Gets the pricing rate of this ticket type.
     *
     * @return the pricing rate of this ticket type.
     */
    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    /**
     * Gets the name of this ticket type.
     *
     * @return the name of this ticket type.
     */
    @Override
    public String toString() {
        return name;
    }
}
