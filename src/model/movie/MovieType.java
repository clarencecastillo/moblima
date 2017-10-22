package model.movie;

import config.TicketConfig;
import model.transaction.Priceable;
import view.ui.EnumerableMenuOption;

public enum MovieType implements Priceable, EnumerableMenuOption {

    TWO_DIMENSION("2D", "2D Movie"),
    THREE_DIMENSION("3D", "3D Movie"),
    BLOCKBUSTER("Blockbuster", "Blockbuster Movie");

    private String string;
    private String description;

    MovieType(String string, String description) {
        this.string = string;
        this.description = description;
    }

    @Override
    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
