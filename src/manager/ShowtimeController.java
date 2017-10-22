package manager;

import config.BookingConfig;
import exception.IllegalBookingStatusException;
import exception.IllegalMovieStatusException;
import exception.IllegalShowtimeStatusException;
import exception.UnpaidBookingChargeException;
import exception.UnpaidPaymentException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import model.booking.Booking;
import model.booking.BookingStatus;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.cinema.Cinema;
import model.cinema.Cineplex;
import model.commons.Language;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;

public class ShowtimeController extends EntityController<Showtime> {

    private static ShowtimeController instance = new ShowtimeController();

    private ShowtimeController() {
        super();
    }

    public static ShowtimeController getInstance() {
        return instance;
    }

    public Showtime createShowtime(UUID movieId, UUID cineplexId, UUID cinemaId, Language language,
                                   Date startTime, boolean noFreePasses,
                                   boolean isPreview, Language[] subtitles) throws IllegalMovieStatusException {

        MovieController movieManager = MovieController.getInstance();
        CinemaController cinemaController = CinemaController.getInstance();
        CineplexController cineplexController = CineplexController.getInstance();

        Movie movie = movieManager.findById(movieId);
        Cineplex cineplex = cineplexController.findById(cineplexId);

        if (movie.getStatus() == MovieStatus.PREVIEW && !isPreview)
            throw new IllegalMovieStatusException("Movie is still in preview");

        if (movie.getStatus() == MovieStatus.END_OF_SHOWING)
            throw new IllegalMovieStatusException("Movie is already not shown");

        Cinema cinema = cinemaController.findById(cinemaId);

        Showtime showtime = new Showtime(movie, cineplex, cinema, language, startTime,
                                         noFreePasses, isPreview, subtitles);

        movie.addShowtime(showtime);
        entities.put(showtime.getId(), showtime);
        return showtime;
    }

    public void cancelShowtime(UUID showtimeId) throws IllegalShowtimeStatusException,
            IllegalMovieStatusException, IllegalBookingStatusException,
        UnpaidBookingChargeException, UnpaidPaymentException {

        BookingController bookingController = BookingController.getInstance();
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
            bookingController.changeBookingStatus(booking.getId(), BookingStatus.CANCELLED);
    }
}
