package model.cinema;

/**
 * Represents a seat of a cinema layout, changed from a cell.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public class Seat extends Cell {

    /**
     * This seat's seat type.
     */
    private SeatType type;

    /**
     * Creates a seat with the given row character and column number and seat type.
     *
     * @param row    This seat's row character.
     * @param column This seat's column number.
     * @param type   This seat's seat type.
     */
    public Seat(char row, int column, SeatType type) {
        super(row, column);
        this.type = type;
    }

    /**
     * Gets this seat's seat type.
     *
     * @return this seat's seat type.
     */
    public SeatType getType() {
        return type;
    }

    /**
     * Changes this seat's seat type.
     *
     * @param type This seat's new seat type.
     */
    public void setType(SeatType type) {
        this.type = type;
    }

    /**
     * Compares this seat to another.
     *
     * @param obj The object to compare to.
     * @param obj The object to compare to.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Seat.class))
            return false;
        Seat seat = (Seat) obj;
        return super.equals(seat) && seat.type == type;
    }

    /**
     * Gets this seat's row and column value as a string.
     *
     * @return This seat's row and column value as a string.
     */
    @Override
    public String toString() {
        return String.valueOf(row) + column;
    }

    /**
     * Gets a hash code value for this seat using the row and column string.
     *
     * @return The hash code value for this seat.
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toStringIcon() {
        String stringIcon = super.toStringIcon();
        switch (type) {

            case SINGLE:
                stringIcon = "[ ]";
                break;
            case COUPLE:
                stringIcon = "[    ]";
                break;
            case HANDICAP:
                stringIcon = "{ }";
                break;
        }
        return stringIcon;
    }
}
