package model.cinema;

import config.TicketConfig;
import model.transaction.Priceable;

public enum SeatType implements Priceable {

    SINGLE("[ ]"),
    COUPLE("[    ]"),
    HANDICAP("( )");

    private String icon;

    SeatType(String icon) {
        this.icon = icon;
    }

    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    @Override
    public String toString() {
        return icon;
    }
}
