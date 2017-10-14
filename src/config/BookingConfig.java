package config;

public class BookingConfig implements Configurable {

    private static BookingConfig instance;
    private static int maxSeatsPerBooking;
    private static int minDaysBeforeOpenBooking;
    private static double bookingSurcharge;
    private static int bookingChangesGraceMinutes;
    private static double bookingChangesSurcharge;
    private static int minutesBeforeClosedBooking;

    private BookingConfig() {
        reset();
    }

    public static BookingConfig getInstance() {
        if (instance == null)
            instance = new BookingConfig();
        return instance;
    }

    public static int getMaxSeatsPerBooking() {
        return maxSeatsPerBooking;
    }

    public static int getMinDaysBeforeOpenBooking() {
        return minDaysBeforeOpenBooking;
    }

    public static double getBookingSurcharrge() {
        return bookingSurcharge;
    }

    public static int getBookingChangesGraceMinutes() {
        return bookingChangesGraceMinutes;
    }

    public static double getBookingChangesSurcharge() {
        return bookingChangesSurcharge;
    }

    public static int getMinutesBeforeClosedBooking() {
        return minutesBeforeClosedBooking;
    }

    public void setMaxSeatsPerBooking(int maxSeatsPerBooking) {
        BookingConfig.maxSeatsPerBooking = maxSeatsPerBooking;
    }

    public void setMinDaysBeforeOpenBooking(int minDaysBeforeOpenBooking) {
        BookingConfig.minDaysBeforeOpenBooking = minDaysBeforeOpenBooking;
    }

    public void setBookingSurcharge(double bookingSurcharge) {
        BookingConfig.bookingSurcharge = bookingSurcharge;
    }

    public void setBookingChangesGraceMinutes(int bookingChangesGraceMinutes) {
        BookingConfig.bookingChangesGraceMinutes = bookingChangesGraceMinutes;
    }

    public void setBookingChangesSurcharge(double bookingChangesSurcharge) {
        BookingConfig.bookingChangesSurcharge = bookingChangesSurcharge;
    }

    public void setMinutesBeforeClosedBooking(int minutesBeforeClosedBooking) {
        BookingConfig.minutesBeforeClosedBooking = minutesBeforeClosedBooking;
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.BOOKING;
    }

    @Override
    public void reset() {
        maxSeatsPerBooking = 10;
        minDaysBeforeOpenBooking = 7;
        bookingSurcharge = 1.0;
        bookingChangesGraceMinutes = 120;
        bookingChangesSurcharge = 1.50;
        minutesBeforeClosedBooking = 0;
    }
}
