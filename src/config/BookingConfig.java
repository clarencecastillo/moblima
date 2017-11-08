package config;

/**
 * Represents the configuration of booking setting.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class BookingConfig implements Configurable {

    /**
     * A reference to this singleton instance.
     */
    private static BookingConfig instance = new BookingConfig();

    /**
     * The maximum seats to be booked for each booking.
     */
    private static int maxSeatsPerBooking;

    /**
     * The number of days before the showtime to open its booking.
     */
    private static int DaysBeforeOpenBooking;

    /**
     * The surcharge fee of each booking.
     */
    private static double bookingSurcharge;

    /**
     * The number of minutes before the showtime to close the booking.
     */
    private static int minutesBeforeClosedBooking;

    /**
     * The buffer minutes after the showtime during which the same cineplex cannot be scheduled another showtime.
     */
    private static int bufferMinutesAfterShowtime;

    /**
     * Initializes the booking configuration by resetting.
     */
    private BookingConfig() {
        reset();
    }

    /**
     * Gets this BookingConfig's singleton instance.
     *
     * @return this BookingConfig's singleton instance.
     */
    public static BookingConfig getInstance() {
        return instance;
    }

    /**
     * Gets the maximum seats to be booked for each booking.
     *
     * @return the maximum seats to be booked for each booking.
     */
    public static int getMaxSeatsPerBooking() {
        return maxSeatsPerBooking;
    }

    /**
     * Changes the maximum seats to be booked for each booking.
     *
     * @param maxSeatsPerBooking The new maximum seats to be booked for each booking.
     */
    public void setMaxSeatsPerBooking(int maxSeatsPerBooking) {
        BookingConfig.maxSeatsPerBooking = maxSeatsPerBooking;
    }

    /**
     * Gets the number of days before the showtime to open its booking.
     *
     * @return the number of days before the showtime to open its booking.
     */
    public static int getDaysBeforeOpenBooking() {
        return DaysBeforeOpenBooking;
    }

    /**
     * Gets the surcharge fee of each booking.
     *
     * @return the surcharge fee of each booking.
     */
    public static double getBookingSurcharrge() {
        return bookingSurcharge;
    }

    /**
     * Gets the number of minutes before the showtime to close the booking.
     *
     * @return the number of minutes before the showtime to close the booking.
     */
    public static int getMinutesBeforeClosedBooking() {
        return minutesBeforeClosedBooking;
    }

    /**
     * Gets the buffer minutes after the showtime.
     * @return the buffer minutes after the showtime.
     */
    public static int getBufferMinutesAfterShowtime() {
        return bufferMinutesAfterShowtime;
    }

    /**
     * Changes the number of minutes before the showtime to close the booking.
     *
     * @param minutesBeforeClosedBooking The new number of minutes before the showtime to close the booking.
     */
    public void setMinutesBeforeClosedBooking(int minutesBeforeClosedBooking) {
        BookingConfig.minutesBeforeClosedBooking = minutesBeforeClosedBooking;
    }

    /**
     * Changes the number of days before the showtime to open its booking.
     *
     * @param DaysBeforeOpenBooking The number of days before the showtime to open its booking.
     */
    public void setMinDaysBeforeOpenBooking(int DaysBeforeOpenBooking) {
        BookingConfig.DaysBeforeOpenBooking = DaysBeforeOpenBooking;
    }

    /**
     * Changes the surcharge fee of each booking.
     *
     * @param bookingSurcharge The new surcharge fee of each booking.
     */
    public void setBookingSurcharge(double bookingSurcharge) {
        BookingConfig.bookingSurcharge = bookingSurcharge;
    }

    /**
     * Sets the buffer minutes after the showtime.
     *
     * @param bufferMinutesAfterShowtime The buffer minutes after the showtime.
     */
    public void setBufferMinutesAfterShowtime(int bufferMinutesAfterShowtime) {
        BookingConfig.bufferMinutesAfterShowtime = bufferMinutesAfterShowtime;
    }

    /**
     * Gets the type of this configuration.
     *
     * @return the type of this configuration which is booking.
     */
    @Override
    public ConfigType getConfigType() {
        return ConfigType.BOOKING;
    }

    /**
     * Resets the booking settings to the default.
     */
    @Override
    public void reset() {
        maxSeatsPerBooking = 10;
        DaysBeforeOpenBooking = 7;
        bookingSurcharge = 1.0;
        minutesBeforeClosedBooking = 0;
        bufferMinutesAfterShowtime = 30;
    }
}
