package util;

import view.ui.Form;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class has many utility functions related to date and time.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Utilities {

    /**
     * Gets a list of dates between the given start date and end date.
     * @param startDate the given starting day of this list of dates.
     * @param endDate  the given ending day of this list of dates.
     * @return a list of dates between the given start date and end date.
     */
    public static List<Date> getDaysBetweenDates(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    /**
     * Gets a date of Date class which is converted from the given date in form of a string.
     * based on the format set by the form.
     * @param date The date to be converted which is in the form of a string.
     * @return the converted date(Date class) of the given string of date.
     */
    public static Date parseDate(String date) {
        return parseDate(date, Form.DATE_FORMAT);
    }

    /**
     * Gets a date of Date class which is converted from the given date in form of a string.
     * according the given string's format.
     * @param date The date to be converted which is in the form of a string.
     * @param format The format of the given string of date.
     * @return the converted date(Date class) of the given string of date.
     */
    public static Date parseDate(String date, String format) {
        try {
            return parseDateIgnoreError(date, format);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets a date of Date class which is converted from the given date in form of a string.
     * Errors will be ignored.
     * @param date The date to be converted which is in the form of a string.
     * @return the converted date(Date class) of the given string of date.
     * @throws ParseException if the date cannot be converted.
     */
    public static Date parseDateIgnoreError(String date) throws ParseException {
        return parseDateIgnoreError(date, Form.DATE_FORMAT);
    }

    /**
     * Gets a date of Date class which is converted from the given date in form of a string according the given format.
     * @param date The date to be converted which is in the form of a string.
     * @param format The format of the given string of date.
     * @return the converted date(Date class) of the given string of date.
     * @throws ParseException if the date cannot be converted.
     */
    public static Date parseDateIgnoreError(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    /**
     * Gets a time with day, hour and minute.
     * @param date The date of the time.
     * @param hour The date of the time.
     * @param minute The minute of the time.
     * @return a time with the given day, hour and minute.
     */
    public static Date getDateWithTime(Date date, int hour, int minute) {

        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gets the starting time with day, hour and minute of a given day.
     * @param date the day of which start time is to be returned.
     * @return the starting time with day, hour and minute of the given day.
     */
    public static Date getStartOfDate(Date date) {
        return getDateWithTime(date, 0, 0);
    }

    /**
     * Gets the ending time with day, hour and minute of a given day.
     * @param date the day of which ending time is to be returned.
     * @return the ending time with day, hour and minute of the given day.
     */
    public static Date getEndOfDate(Date date) {
        return getDateWithTime(date, 23, 59);
    }

    /**
     * Gets the date after the given date by the given amount of the given calendar field.
     * @param date The given date after which the date is to be returned.
     * @param calendarField The given calender filed of which is to be used.
     * @param amount The given amount of the calender field to be used.
     * @return the date after the given date by the given amount of the given calendar field.
     */
    public static Date getDateAfter(Date date, int calendarField, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, amount);
        return calendar.getTime();
    }

    /**
     * Gets the date before the given date by the given amount of the given calendar field.
     * @param date The given date after which the date is to be returned.
     * @param calendarField The given calender filed of which is to be used.
     * @param amount The given amount of the calender field to be used.
     * @return the date before the given date by the given amount of the given calendar field.
     */
    public static Date getDateBefore(Date date, int calendarField, int amount) {
        return getDateAfter(date, calendarField, -1 * amount);
    }

    /**
     * Gets the string of a date in a given format.
     * @param date The date to be converted.
     * @param format The given format of the string.
     * @return the string of this date in the given format.
     */
    public static String toFormat(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * Gets the string of a date in the format set by the form.
     * @param date The date to be converted.
     * @return the string of this date in the given format in the form.
     */
    public static String toFormat(Date date) {
        return toFormat(date, Form.DATE_FORMAT);
    }

    /**
     * Checks whether a date falls on a list of days of the week.
     * @param date The day to be checked.
     * @param calendarDaysOfWeek The day of the week in calender.
     * @return true if the given date falls on the given days of the week.
     */
    public static boolean dateFallsOn(Date date, int... calendarDaysOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return Arrays.stream(calendarDaysOfWeek).anyMatch(calendarDayOfWeek ->
                calendarDayOfWeek == calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static boolean isOnSameDay(Date date1, Date date2) {
        return getDateWithTime(date1, 0, 0).compareTo(getDateWithTime(date2, 0, 0)) == 0;
    }

    /**
     * Gets the similarity score of two strings.
     * @param a The first string to be compared.
     * @param b The second string to be compared.
     * @return the similarity score of these two strings.
     */
    public static int fuzzyScore(String a, String b) {

        final String termLowerCase = a.toLowerCase();
        final String queryLowerCase = b.toLowerCase();

        // the resulting score
        int score = 0;

        // the position in the term which will be scanned next for potential
        // query character matches
        int termIndex = 0;

        // index of the previously matched character in the term
        int previousMatchingCharacterIndex = Integer.MIN_VALUE;

        for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
            final char queryChar = queryLowerCase.charAt(queryIndex);

            boolean termCharacterMatchFound = false;
            for (; termIndex < termLowerCase.length()
                    && !termCharacterMatchFound; termIndex++) {
                final char termChar = termLowerCase.charAt(termIndex);

                if (queryChar == termChar) {
                    // simple character matches result in one point
                    score++;

                    // subsequent character matches further improve
                    // the score.
                    if (previousMatchingCharacterIndex + 1 == termIndex) {
                        score += 2;
                    }

                    previousMatchingCharacterIndex = termIndex;

                    // we can leave the nested loop. Every character in the
                    // query can match at most one character in the term.
                    termCharacterMatchFound = true;
                }
            }
        }

        return score;
    }

    // TODO Javadoc
    public static ObjectOutputStream getObjectOutputStream(String filename) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return objectOutputStream;
    }

    // TODO Javadoc
    public static ObjectInputStream getObjectInputStream(String filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
        return objectInputStream;
    }

    // TODO Javadoc
    public static boolean overlaps(Date start1, Date end1, Date start2, Date end2){
        return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
    }
}
