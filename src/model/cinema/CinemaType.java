package model.cinema;

import config.TicketConfig;
import model.booking.TicketType;
import model.transaction.Priceable;

public enum CinemaType implements Priceable {

    REGULAR("Regular"),
    PLATINUM("Platinum"),
    EXECUTIVE("Executive");

    private String name;

    CinemaType(String name) {
        this.name = name;
    }

    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    public TicketType[] getTicketTypes() {
        return TicketConfig.getAvailableTicketTypes(this);
    }

    public boolean isAvailable(TicketType type) {
        return TicketConfig.isAvailable(this, type);
    }

    @Override
    public String toString() {
        return name;
    }
}
