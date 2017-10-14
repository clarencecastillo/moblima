package model.movie;

import config.TicketConfig;
import model.transaction.Priceable;

public enum MovieType implements Priceable {

    TWO_DIMENSIOM("2D"),
    THREE_DIMENSION("3D");

    private String name;

    MovieType(String name) {
        this.name = name;
    }

    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
