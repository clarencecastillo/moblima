package model.booking;

/**
 * Represents a standard set of showtime status.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum ShowtimeStatus {

    CANCELLED("Cancelled"),
    OPEN_BOOKING("Open Booking"),
    CLOSED_BOOKING("Closed Booking");

    /**
     * The name of this showtime status.
     */
    private String name;

    /**
     * Creates a showtime status with s given status name.
     *
     * @param name The name of this showtime status.
     */
    ShowtimeStatus(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this showtime status.
     *
     * @return the name of this showtime status.
     */
    @Override
    public String toString() {
        return name;
    }
}
