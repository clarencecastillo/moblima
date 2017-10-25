package model.booking;

/**
 Represents a standard set of seating status and their representation on the layout graph.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public enum SeatingStatus {

    /**
     * The status of an available seating.
     */
    AVAILABLE(" "),

    /**
     * The status of a taken seating.
     */
    TAKEN("X"),

    /**
     * The status of a seating under maintenance.
     */
    MAINTENANCE("!"),

    /**
     * The status of a reserved seating.
     */
    RESERVED("-");

    /**
     * The icon representation of this seating.
     */
    private String icon;

    /**
     * Creates a seating with the given icon.
     * @param icon The icon of this seating.
     */
    SeatingStatus(String icon) {
        this.icon = icon;
    }

    /**
     * Gets the icon of this seating.
     * @return the icon of this seating.
     */
    @Override
    public String toString() {
        return icon;
    }
}
