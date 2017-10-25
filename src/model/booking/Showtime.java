package model.booking;

import config.BookingConfig;
import model.cinema.Cinema;
import model.cinema.Cineplex;
import model.commons.Entity;
import model.commons.Language;
import model.movie.Movie;
import util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

    public ShowtimeSeating getSeating() {
        return seating;
    }

    public void setSeating(ShowtimeSeating seating) {
        this.seating = seating;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

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

    public ArrayList<Language> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(ArrayList<Language> subtitles) {
        this.subtitles = subtitles;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

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

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking.getId());
    }
}
