package model.booking;

/**
 * Represents a standard set of booking status.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public enum BookingStatus {

    /**
     * The booking is in the status of in progress when it is initially creates after the user starts to make booking.
     */
    IN_PROGRESS("In Progress"),

    /**
     * The booking is in the status of cancelled when it is cancelled by the user.
     */
    CANCELLED("Cancelled"),

    /**
     * The booking is in the status of confirmed when it is confirmed by the user .
     */
    CONFIRMED("Confirmed");

    /**
     * The name of this booking status.
     */
    private String name;

    /**
     * Creates a booking status with s given status name.
     *
     * @param name The name of this booking status.
     */
    BookingStatus(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this booking status.
     *
     * @return the name of this booking status.
     */
    @Override
    public String toString() {
        return name;
    }
}
