package model.movie;

import config.TicketConfig;
import model.transaction.Priceable;

public enum MovieType implements Priceable {

    TWO_DIMENSION("2D"),
    THREE_DIMENSION("3D"),
    BLOCKBUSTER("Blockbuster");

    private String string;

    MovieType(String string) {
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
