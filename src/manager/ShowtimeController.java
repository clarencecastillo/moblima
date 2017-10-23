package manager;

import config.BookingConfig;
import exception.IllegalBookingStatusException;
import exception.IllegalMovieStatusException;
import exception.IllegalShowtimeStatusException;
import exception.UnpaidBookingChargeException;
import exception.UnpaidPaymentException;

import java.util.*;

import model.booking.*;
import model.cinema.Cinema;
import model.cinema.Cineplex;
import model.commons.Language;
import model.movie.Movie;
import model.movie.MovieStatus;
import model.transaction.Priceable;
import model.transaction.Pricing;
import util.Utilities;

public class ShowtimeController extends EntityController<Showtime> {

    private static ShowtimeController instance = new ShowtimeController();

    private MovieController movieManager = MovieController.getInstance();
    private CinemaController cinemaController = CinemaController.getInstance();
    private CineplexController cineplexController = CineplexController.getInstance();
    private BookingController bookingController = BookingController.getInstance();

    private ShowtimeController() {
        super();
    }

    public static ShowtimeController getInstance() {
        return instance;
    }

    public Showtime createShowtime(UUID movieId, UUID cineplexId, UUID cinemaId, Language language,
                                   Date startTime, boolean noFreePasses,
                                   boolean isPreview, Language[] subtitles) throws IllegalMovieStatusException {

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
        cineplex.addShowtime(showtime);
        entities.put(showtime.getId(), showtime);
        return showtime;
    }

    public void cancelShowtime(UUID showtimeId) throws IllegalShowtimeStatusException,
            IllegalMovieStatusException, IllegalBookingStatusException,
        UnpaidBookingChargeException, UnpaidPaymentException {

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
            bookingController.cancelBooking(booking.getId());
    }

    public ArrayList<Showtime> findByStatus(ShowtimeStatus statuses) {
        ArrayList<Showtime> showtimes = new ArrayList<>();
        List<ShowtimeStatus> statusFilter = Arrays.asList(statuses);
        for (Showtime showtime : entities.values())
            if (statusFilter.contains(showtime.getStatus()))
                showtimes.add(showtime);
        return showtimes;
    }

    public ArrayList<Showtime> findByCineplexAndMovie(Cineplex cineplex, Movie movie) {
        ArrayList<Showtime> showtimes = new ArrayList<>();
        for (Showtime showtime : cineplex.getShowtimes())
            if (showtime.getMovie().equals(movie))
                showtimes.add(showtime);
        return showtimes;
    }

    public double getTicketTypePricing(Showtime showtime, TicketType ticketType) {
        return Priceable.getPrice(ticketType,showtime.getCinema().getType(),showtime.getMovie().getType());
    }

}
