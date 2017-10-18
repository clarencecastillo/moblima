package config;

import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;
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
    }
}
