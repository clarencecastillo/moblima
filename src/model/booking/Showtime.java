package model.booking;

import config.BookingConfig;
import model.cinema.Cinema;
import model.cinema.Cineplex;
import model.commons.Entity;
import model.commons.Language;
import model.movie.Movie;
import model.transaction.Priceable;
import util.Utilities;

import java.util.*;

/**
 * Represents the showtime of a movie in a Cineplex.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Showtime extends Entity implements Comparable<Showtime> {

    /**
     * The movie shown for this showtime.
     */
    private Movie movie;

    /**
     * The cineplex where the showtime is at.
     */
    private Cineplex cineplex;

    /**
     * The cinema where the showtime is at.
     */
    private Cinema cinema;

    /**
     * The seating of this showtime.
     */
    private ShowtimeSeating seating;

    /**
     * The language of this showtime.
     */
    private Language language;

    /**
     * The starting time of this showtime.
     */
    private Date startTime;

    /**
     * Whether this showtime can use coupons.
     */
    private boolean noFreePasses;

    /**
     * Whether this showtime is a preview.
     */
    private boolean isPreview;

    /**
     * Whether this showtime is cancelled.
     */
    private boolean isCancelled;

    /**
     * The subtitles of this showtime.
     */
    private ArrayList<Language> subtitles;

    /**
     * The bookings of this showtime.
     */
    private ArrayList<Booking> bookings;

    /**
     * Creates a showtime with the given information.
     * @param movie The movie shown for this showtime.
     * @param cineplex The cineplex where the showtime is at.
     * @param cinema The cinema where the showtime is at.
     * @param language The language of this showtime.
     * @param startTime The starting time of this showtime.
     * @param noFreePasses Whether this showtime can use coupons.
     * @param isPreview Whether this showtime is a preview.
     * @param subtitles The subtitles of this showtime.
     */
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
        this.isCancelled = isCancelled;
        this.subtitles = new ArrayList<Language>();
        this.subtitles.addAll(Arrays.asList(subtitles));
        this.bookings = new ArrayList<Booking>();
    }

    /**
     * Gets the movie of this showtime.
     * @return the movie of this showtime.
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * Changes the movie of this showtime.
     * @param movie the new movie of this showtime.
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Gets the cineplex of this showtime.
     * @return The cineplex of this showtime.
     */
    public Cineplex getCineplex() {
        return cineplex;
    }

    /**
     * Changes the cineplex of this showtime.
     * @param cineplex The cineplex of this showtime.
     */
    public void setCineplex(Cineplex cineplex) {
        this.cineplex = cineplex;
    }

    /**
     * Gets the cinema of this showtime.
     * @return The cincinemaeplex of this showtime.
     */
    public Cinema getCinema() {
        return cinema;
    }

    /**
     * Changes the cinema of this showtime.
     * @param cinema the new cinema of this showtime.
     */
    public void setCinema(Cinema cinema) { this.cinema = cinema; }

    /**
     * Gets the seating of this showtime.
     * @return the seating of this showtime.
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
     * @return the language of this showtime.
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
     * Checks whether this showtime does not allow free pass.
     * @return true if this showtime does allows free pass.
     */
    public boolean isNoFreePasses() {
        return noFreePasses;
    }

    /**
     * Changes the setting of whether this showtime allows free pass.
     * @param noFreePasses The new setting of free pass.
     */
    public void setNoFreePasses(boolean noFreePasses) {
        this.noFreePasses = noFreePasses;
    }

    /**
     * Checks whether it is a preview.
     * @return true if this is a preview.
     */
    public boolean isPreview() {
        return isPreview;
    }

    /**
     * Changes the setting of whether this showtime is a preview.
     * @param preview the setting of whether this showtime is a preview.
     */
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

    /**
     * Gets the pricing of a ticket type for this showtime.
     * @param ticketType The ticket type to be checked.
     * @return the pricing of a ticket type for a given show time.
     */
    public double getTicketTypePricing(TicketType ticketType) {
        return Priceable.getPrice(ticketType, getCinema().getType(), getMovie().getType());
    }

    /**
     * Checks whether this showtime is cancelled.
     * @return true if this showtime is cancelled.
     */
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

    @Override
    public int compareTo(Showtime o) {
        return startTime.compareTo(o.startTime);
    }
}
