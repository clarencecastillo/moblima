package config;

import view.ui.Describable;

public enum ConfigType implements Describable {
    HOLIDAY("Holidays"),
    TICKET("Ticket Pricing and Types"),
    BOOKING("Booking"),
    PAYMENT("Payment"),
    ADMIN("Administration");

    private String description;
    ConfigType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
