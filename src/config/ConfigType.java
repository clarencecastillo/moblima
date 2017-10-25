package config;

import view.ui.EnumerableMenuOption;

/**
 * Represents a standard set of system settings that can be configured by the admin.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum ConfigType implements EnumerableMenuOption {

    /**
     * The days of holidays.
     */
    HOLIDAY("Holidays"),

    /**
     * The pricing rate of the ticket type.
     */
    TICKET("Ticket Pricing and Types"),

    /**
     * The configuration related to booking.
     */
    BOOKING("Booking"),

    /**
     * The configuration related to payment.
     */
    PAYMENT("Payment"),

    /**
     * The configuration related to administration.
     */
    ADMIN("Administration");

    /**
     * The description of this configuration type.
     */
    private String description;

    /**
     * Creates a type of configuration with the given description.
     *
     * @param description the description of this configuration type.
     */
    ConfigType(String description) {
        this.description = description;
    }

    /**
     * Gets the description of this configuration type.
     *
     * @return the description of this configuration type.
     */
    @Override
    public String getDescription() {
        return description;
    }
}
