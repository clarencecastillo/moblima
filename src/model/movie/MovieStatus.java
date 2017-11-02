package model.movie;

import view.ui.EnumerableMenuOption;

/**
 * Represents a standard set of movie status.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public enum MovieStatus implements EnumerableMenuOption {

    /**
     * The status of coming soon.
     */
    COMING_SOON("Coming Soon"),

    /**
     * The status of preview.
     */
    PREVIEW("Preview"),

    /**
     * The status of now showing.
     */
    NOW_SHOWING("Now Showing"),

    /**
     * The status of end of showing.
     */
    END_OF_SHOWING("End of Showing");

    /**
     * The name of this movie status.
     */
    private String name;

    /**
     * Creates the movie status with the given status name.
     *
     * @param name the name of this movie status.
     */
    MovieStatus(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this movie status.
     *
     * @return the name of this movie status.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets the name of this movie status.
     *
     * @return the name of this movie status.
     */
    @Override
    public String getDescription() {
        return name;
    }
}
