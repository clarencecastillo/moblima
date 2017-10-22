package config;

import view.ui.EnumerableMenuOption;

public enum ConfigType implements EnumerableMenuOption {
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
