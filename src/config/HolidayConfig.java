package moblima.config;

import moblima.exception.IllegalActionException;
import moblima.util.Utilities;

import java.util.Date;
import java.util.Hashtable;

/**
 * Represents the configuration of holidays setting.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class HolidayConfig implements Configurable {

    /**
     * A reference to this singleton instance.
     */
    private static HolidayConfig instance = new HolidayConfig();

    /**
     * A hash table of holidays with the date as the key and the holiday name as the value.
     */
    private static Hashtable<Date, String> holidays;

    /**
     * Initializes the holiday configuration by resetting.
     */
    private HolidayConfig() {
        reset();
    }

    /**
     * Gets this BookingConfig's singleton instance.
     *
     * @return this BookingConfig's singleton instance.
     */
    public static HolidayConfig getInstance() {
        return instance;
    }

    /**
     * Gets the hash table of holidays with the date as the key and the holiday name as the value.
     *
     * @return the hash table of holidays with the date as the key and the holiday name as the value.
     */
    public static Hashtable<Date, String> getHolidays() {
        return holidays;
    }

    /**
     * Checks whether a date is a holiday in the holiday setting.
     *
     * @param date The date to be checked.
     * @return true is the date appears in the holiday setting.
     */
    public static boolean isHoliday(Date date) {
        String dateString = Utilities.toFormat(date, "dd/MM");
        for (Date holidayDate : holidays.keySet()) {
            if (Utilities.toFormat(holidayDate, "dd/MM").equals(dateString))
                return true;
        }
        return false;
    }

    /**
     * Adds a holiday to the holiday setting.
     *
     * @param date The date to be added.
     * @param name The name of the holiday to be added.
     */
    public void setHoliday(Date date, String name) {
        holidays.put(Utilities.getStartOfDate(date), name);
    }

    /**
     * Removes a holiday from the holiday settings.
     *
     * @param date The date to be removed.
     * @exception IllegalActionException if the day to be removed is not a holiday by the original setting.
     */
    public void unsetHoliday(Date date) throws IllegalActionException {
        if (!isHoliday(date)) {
            throw new IllegalActionException("This day is not a holiday.");
        }
        holidays.remove(Utilities.getStartOfDate(date));
    }

    /**
     * Gets the type of this configuration.
     *
     * @return the type of this configuration which is holiday.
     */
    @Override
    public ConfigType getConfigType() {
        return ConfigType.HOLIDAY;
    }

    /**
     * Resets the holiday settings to the default.
     */
    @Override
    public void reset() {
        holidays = new Hashtable<Date, String>();
        holidays.put(Utilities.parseDate("01/01", "dd/MM"), "New Year's Day");
        holidays.put(Utilities.parseDate("28/01", "dd/MM"), "Chinese New Year");
        holidays.put(Utilities.parseDate("14/04", "dd/MM"), "Good Friday");
        holidays.put(Utilities.parseDate("01/05", "dd/MM"), "Labour Day");
        holidays.put(Utilities.parseDate("10/05", "dd/MM"), "Vesak Day");
        holidays.put(Utilities.parseDate("25/06", "dd/MM"), "Hari Raya Puasa");
        holidays.put(Utilities.parseDate("09/08", "dd/MM"), "National Day");
        holidays.put(Utilities.parseDate("01/09", "dd/MM"), "Hari Raya Haji");
        holidays.put(Utilities.parseDate("18/10", "dd/MM"), "Deepavali");
        holidays.put(Utilities.parseDate("25/12", "dd/MM"), "Christmas Day");

    }
}
