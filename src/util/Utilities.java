package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilities {

    public static Date getDateWithTime(Date date, int hour, int minute) {

        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getStartOfDate(Date date) {
        return getDateWithTime(date, 0, 0);
    }

    public static Date getEndOfDate(Date date) {
        return getDateWithTime(date, 23, 59);
    }

    public static Date getDateAfter(Date date, int calendarField, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, amount);
        return calendar.getTime();
    }

    public static Date getDateBefore(Date date, int calendarField, int amount) {
        return getDateAfter(date, calendarField, -1 *amount);
    }

    public static String toFormat(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static boolean dateFallsOn(Date date, int calendarDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == calendarDayOfWeek;
    }

    public static int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                                  a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
