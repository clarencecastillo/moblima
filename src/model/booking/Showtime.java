package model.booking;

import config.BookingConfig;
import model.cinema.Cinema;
import model.cinema.Cineplex;
import model.commons.Entity;
import model.commons.Language;
import model.movie.Movie;
import util.Utilities;

import java.util.*;

public class Showtime extends Entity {

    private Movie movie;
    private Cineplex cineplex;
    private Cinema cinema;
    private ShowtimeSeating seating;
    private Language language;
    private Date startTime;
    private boolean noFreePasses; // can't use coupons
    private boolean isPreview;
    private boolean isCancelled;
    private ArrayList<Language> subtitles;
    private ArrayList<Booking> bookings;

    public Showtime(Movie movie, Cineplex cineplex, Cinema cinema, Language language,
                    Date startTime, boolean noFreePasses,
                    boolean isPreview, Language[] subtitles) {
        this.movie = movie;
        this.cineplex = cineplex;
        this.cinema = cinema;
        this.seating = new ShowtimeSeating(this);
        this.language = language;
        this.startTime = startTime;
        this.noFreePasses = noFreePasses;
        this.isPreview = isPreview;
        this.subtitles = new ArrayList<Language>();
        this.subtitles.addAll(Arrays.asList(subtitles));
        this.bookings = new ArrayList<Booking>();
        this.isCancelled = false;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Cineplex getCineplex() {
        return cineplex;
    }

    public void setCineplex(Cineplex cineplex) {
        this.cineplex = cineplex;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    /**
     *
     * @return
     */
    public ShowtimeSeating getSeating() {
        return seating;
    }

    /**
     * Changes the seating of this showtime.
     * @param seating The new seating of this showtime.
     */
    public void setSeating(ShowtimeSeating seating) {
        this.seating = seating;
    }

    /**
     * Gets the language of this showtime.
     * @return
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Changes the language of this showtime.
     * @param language The new language of this showtime.
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Gets the starting time of this showtime.
     * @return the new starting time of this showtime.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Changes the stating time of this showtime.
     * @param startTime The new starting time of this showtime.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Checks whether this showtime allows free pass.
     * @return true if this showtime allows free pass.
     */
    public boolean isNoFreePasses() {
        return noFreePasses;
    }

    public void setNoFreePasses(boolean noFreePasses) {
        this.noFreePasses = noFreePasses;
    }

    public boolean isPreview() {
        return isPreview;
    }

    public void setPreview(boolean preview) {
        isPreview = preview;
    }

    /**
     * Gets the subtitles of this showtime.
     * @return the subtitles of this showtime.
     */
    public ArrayList<Language> getSubtitles() {
        return subtitles;
    }

    /**
     * Changes the subtitles of this showtime.
     * @param subtitles The new subtitles of this showtime.
     */
    public void setSubtitles(ArrayList<Language> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Gets the list of booking of this showtime.
     * @return the list of booking of this showtime.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Gets the status of this showtime.
     * @return the status of this showtime.
     */
    public ShowtimeStatus getStatus() {
        if (isCancelled) return ShowtimeStatus.CANCELLED;

        ShowtimeStatus showtimeStatus = ShowtimeStatus.OPEN_BOOKING;

        int minDaysBeforeOpenBooking = BookingConfig.getDaysBeforeOpenBooking();
        Date openBookingDate = Utilities.getDateBefore(startTime,
                Calendar.DAY_OF_YEAR,
                minDaysBeforeOpenBooking);
        int minutesBeforeClosedBooking = BookingConfig.getMinutesBeforeClosedBooking();
        Date lastBookingMinute = Utilities.getDateBefore(startTime, Calendar.MINUTE,
                minutesBeforeClosedBooking);
        Date now = new Date();
        if (now.before(Utilities.getStartOfDate(openBookingDate)) || now.after(lastBookingMinute))
            showtimeStatus = ShowtimeStatus.CLOSED_BOOKING;

        return showtimeStatus;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sets the status of a booking to be cancelled.
     * @param cancelled The booking to be cancelled.
     */
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    /**
     * Add a booking to this showtime.
     * @param booking The booking to be added to this showtime.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Remove a booking from this showtime.
     * @param booking The booking to be removed from this showtime.
     */
    public void removeBooking(Booking booking) { bookings.remove(booking.getId()); }
}
