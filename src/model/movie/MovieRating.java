package model.movie;

import view.ui.EnumerableMenuOption;

/**
 * Represents a standard set of rating of a movie.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum MovieRating implements EnumerableMenuOption {

    /**
     * The rating of G.
     */
    G("G"),

    /**
     * The rating of PG.
     */
    PG("PG"),

    /**
     * The rating of PG13.
     */
    PG13("PG13"),

    /**
     * The rating of NC16.
     */
    NC16("NC16"),

    /**
     * The rating of M18.
     */
    M18("M18"),

    /**
     * The rating of R21.
     */
    R21("R21");

    /**
     * The name of this rating.
     */
    private String name;

    /**
     * Creates the rating with the given rating name.
     *
     * @param name The given rating name of this movie.
     */
    MovieRating(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this movie rating.
     *
     * @return the name of this movie rating.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the name of this movie rating.
     *
     * @return the name of this movie rating.
     */
    @Override
    public String getDescription() {
        return name;
    }
}
