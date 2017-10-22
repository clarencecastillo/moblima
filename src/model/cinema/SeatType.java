package model.cinema;

import config.TicketConfig;
import model.transaction.Priceable;

public enum SeatType implements Priceable {

    SINGLE("Single"),
    COUPLE("Couple"),
    HANDICAP("Handicap");

    private String string;

    SeatType(String string) {
        this.string = string;
    }

    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    @Override
    public String toString() {
        return string;
    }
}
