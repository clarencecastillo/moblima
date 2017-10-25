package model.cinema;

/**
 * Represents a standard set of seat types for a seat, which can be single, couple, or handicap.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum SeatType {

    /**
     * The seat type of single seat.
     */
    SINGLE("Single"),

    /**
     * /**
     * The seat type of couple seat.
     */
    COUPLE("Couple"),

    /**
     * /**
     * The seat type of handicap seat.
     */
    HANDICAP("Handicap");

    /**
     * The name of this seat type.
     */
    private String string;

    /**
     * Creates a standard seat type with a given type name.
     *
     * @param string The given type name of this seat type.
     */
    SeatType(String string) {
        this.string = string;
    }

    /**
     * Gets this seat's type name.
     *
     * @return This seat's type name.
     */
    @Override
    public String toString() {
        return string;
    }
}
