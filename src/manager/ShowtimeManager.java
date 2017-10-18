package manager;

import config.BookingConfig;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import manager.exception.*;
import model.booking.Booking;
import model.booking.BookingStatus;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.cinema.Cinema;
import model.commons.Language;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;

public class ShowtimeManager extends EntityManager<Showtime> {

    private static ShowtimeManager instance = new ShowtimeManager();

    private ShowtimeManager() {
        super();
    }

    public static ShowtimeManager getInstance() {
        return instance;
    }

    public Showtime createShowtime(UUID movieId, UUID cinemaId, Language language,
                                   Date startTime, Date endTime, boolean noFreePasses,
                                   boolean isPreview, Language[] subtitles) throws IllegalMovieStatusException {

        MovieManager movieManager = MovieManager.getInstance();
        CinemaManager cinemaManager = CinemaManager.getInstance();

        Movie movie = movieManager.findById(movieId);

        if (movie.getStatus() == MovieStatus.PREVIEW && !isPreview)
            throw new IllegalMovieStatusException("Movie is still in preview");

        if (movie.getStatus() == MovieStatus.END_OF_SHOWING)
            throw new IllegalMovieStatusException("Movie is already not shown");

        Cinema cinema = cinemaManager.findById(cinemaId);

        Showtime showtime = new Showtime(movie, cinema, language, startTime, endTime,
                                         noFreePasses, isPreview, subtitles);

        movie.addShowtime(showtime);
        entities.put(showtime.getId(), showtime);
        return showtime;
    }

    public void cancelShowtime(UUID showtimeId) throws IllegalShowtimeStatusException,
            IllegalMovieStatusException, IllegalBookingStatusException,
            UnpaidBookingChargeException, UnpaidPaymentException, IllegalBookingChangeException {

        BookingManager bookingManager = BookingManager.getInstance();
        Showtime showtime = findById(showtimeId);

        if (showtime.getStatus() == ShowtimeStatus.CANCELLED)
            throw new IllegalShowtimeStatusException("Showtime is already cancelled");

        int minutesBeforeClosedBooking = BookingConfig.getMinutesBeforeClosedBooking();
        Date lastBookingMinute = Utilities.getDateBefore(showtime.getStartTime(), Calendar.MINUTE,
                                                         minutesBeforeClosedBooking);
        Date now = new Date();
        if (now.after(lastBookingMinute))
            throw new IllegalShowtimeStatusException("Last booking minute is already passed.");

        showtime.setCancelled(true);
        for (Booking booking: showtime.getBookings())
            bookingManager.changeBookingStatus(booking.getId(), BookingStatus.CANCELLED);
    }
}
