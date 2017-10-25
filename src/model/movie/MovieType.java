package model.movie;

import config.TicketConfig;
import model.transaction.Priceable;
import view.ui.EnumerableMenuOption;

/**
 * Represents a standard set of type of a movie those pricing rate will be added to the ticket price.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum MovieType implements Priceable, EnumerableMenuOption {

    /**
     * The type of 2D.
     */
    TWO_DIMENSION("2D", "2D Movie"),

    /**
     * The type of 3D.
     */
    THREE_DIMENSION("3D", "3D Movie"),

    /**
     * The type of blockbuster.
     */
    BLOCKBUSTER("Blockbuster", "Blockbuster Movie");

    /**
     * The name of this movie type.
     */
    private String string;

    /**
     * The description of this movie type.
     */
    private String description;

    /**
     * Creates the movie type with the movie type name and description.
     *
     * @param string      The name of this movie type.
     * @param description The description of this movie type.
     */
    MovieType(String string, String description) {
        this.string = string;
        this.description = description;
    }

    /**
     * Gets the pricing rate of this movie type.
     *
     * @return the pricing rate of this movie type.
     */
    @Override
    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    /**
     * Gets the movie type name of this movie type.
     *
     * @return the movie type name of this movie type.
     */
    @Override
    public String toString() {
        return string;
    }

    /**
     * Gets the movie type description of this movie type.
     *
     * @return the movie type description of this movie type.
     */
    @Override
    public String getDescription() {
        return description;
    }
}
