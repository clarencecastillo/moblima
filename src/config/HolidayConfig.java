package config;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import util.Utilities;

public class HolidayConfig implements Configurable {

    private static HolidayConfig instance = new HolidayConfig();
    private static Hashtable<Date, String> holidays;

    private HolidayConfig() {
        reset();
    }

    public static HolidayConfig getInstance() {
        return instance;
    }

    public static Hashtable<Date, String> getHolidays() {
        return holidays;
    }

    public static boolean isHoliday(Date date) {
        return holidays.containsKey(Utilities.getStartOfDate(date));
    }

    public void setHoliday(Date date, String name) {
        holidays.put(Utilities.getStartOfDate(date), name);
    }

    public void unsetHoliday(Date date) {
        if (!isHoliday(date)) {
            return; // TODO ERROR
        }
        holidays.remove(Utilities.getStartOfDate(date));
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.HOLIDAY;
    }

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
