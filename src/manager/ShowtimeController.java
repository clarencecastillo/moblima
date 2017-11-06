package manager;

import config.BookingConfig;
import config.HolidayConfig;
import exception.IllegalActionException;
import exception.UninitialisedSingletonException;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.booking.TicketType;
import model.cinema.Cinema;
import model.cinema.Cineplex;
import model.commons.Language;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;

import java.util.*;
import java.util.stream.Collectors;

/**
 Represents the controller of showtimes.
 @version 1.0
 @since 2017-10-20
 */
public class ShowtimeController extends EntityController<Showtime> {

    /**
     * A reference to this singleton instance.
     */
    private static ShowtimeController instance;

    /**
     * Creates the showtime controller.
     */
    private ShowtimeController() {
        super();
    }

    /**
     * Initialize the showtime controller.
     */
    public static void init() {
        instance = new ShowtimeController();
    }

    /**
     * Gets this Showtime Controller's singleton instance.
     * @return this Showtime Controller's singleton instance.
     */
    public static ShowtimeController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }


    /**
     * Creates a showtime with the given information.
     * @param movieId The ID of the movie of this showtime.
     * @param cineplexId The ID of the cineplex of this showtime.
     * @param cinemaId The ID of the cinema of this showtime.
     * @param language The language of this showtime.
     * @param startTime The start time of this showtime.
     * @param isPreview The ID of the movie of this showtime.
     * @param subtitles The ID of the movie of this showtime.
     * @return The ID of the movie of this showtime.
     * @throws IllegalActionException
     */
    /**
     * Creates a showtime with the given information.
     * @param movieId The ID of the movie of this showtime.
     * @param cineplexId The ID of the cineplex of this showtime.
     * @param cinemaId The ID of the cinema of this showtime.
     * @param language The language of this showtime.
     * @param startTime The start time of this showtime.
     * @param isPreview Whether this showtime is a preview.
     * @param noFreePasses Whether this showtime allows free pass.
     * @param subtitles The subtitles of this showtime.
     * @return the newly created showtime.
     * @throws IllegalActionException if the given movie is currently in preview
     * but the showtime to be created is not a preview,
     * or if the movie already ends showing, or if the cinema is not available.
     */
    public Showtime createShowtime(UUID movieId, UUID cineplexId, UUID cinemaId, Language language,
                                   Date startTime, boolean noFreePasses, Language[] subtitles)
            throws IllegalActionException {

        MovieController movieController = MovieController.getInstance();
        CineplexController cineplexController = CineplexController.getInstance();
        CinemaController cinemaController = CinemaController.getInstance();

        Movie movie = movieController.findById(movieId);
        Cineplex cineplex = cineplexController.findById(cineplexId);
        Date endTime = Utilities.getDateAfter(startTime, Calendar.MINUTE,
                movie.getRuntimeMinutes() + BookingConfig.getBufferMinutesAfterShowtime());

        if (movie.getStatus() == MovieStatus.END_OF_SHOWING)
            throw new IllegalActionException("Cannot create showtime for movies in end of showing state");

        if (!cinemaController.isAvaiableOn(cineplexId, cinemaId, startTime, endTime))
            throw new IllegalActionException("There is already a showtime scheduled for this cinema");

        Cinema cinema = cinemaController.findById(cinemaId);
        Showtime showtime = new Showtime(movie, cineplex, cinema, language, startTime,
                movie.getStatus() == MovieStatus.PREVIEW, noFreePasses, subtitles);

        movie.addShowtime(showtime);
        cineplex.addShowtime(showtime);
        entities.put(showtime.getId(), showtime);
        return showtime;
    }

    /**
     * Cancel a showtime, setting its status to cancelled and cancel all its bookings.
     * @param showtimeId The ID of the showtime to be cancelled.
     * @throws IllegalActionException if the showtime is already cancelled,
     * or if the last booking minute has already passed.
     */
    public void cancelShowtime(UUID showtimeId) throws IllegalActionException {

        BookingController bookingController = BookingController.getInstance();

        Showtime showtime = findById(showtimeId);

        if (showtime.getStatus() == ShowtimeStatus.CANCELLED)
            throw new IllegalActionException("Showtime is already cancelled");

        int minutesBeforeClosedBooking = BookingConfig.getMinutesBeforeClosedBooking();
        Date lastBookingMinute = Utilities.getDateBefore(showtime.getStartTime(), Calendar.MINUTE,
                minutesBeforeClosedBooking);
        Date now = new Date();
        if (now.after(lastBookingMinute))
            throw new IllegalActionException("Last booking minute is already passed.");

        showtime.setCancelled(true);
        for (Booking booking : showtime.getBookings())
            bookingController.cancelBooking(booking.getId());
    }

    /**
     * Gets a list of showtime in the given cineplex that shows the given movie.
     * @param cineplexId The ID of the cineplex of the showtime to be returned.
     * @param movieId The ID of the movie of the showtime to be returned.
     * @return a list of showtime in the given cineplex that shows the given movie.
     */
    public List<Showtime> findByCineplexAndMovie(UUID cineplexId, UUID movieId) {
        CineplexController cineplexController = CineplexController.getInstance();
        Cineplex cineplex = cineplexController.findById(cineplexId);

        MovieController movieController = MovieController.getInstance();
        Movie movie = movieController.findById(movieId);

        ArrayList<Showtime> showtimes = new ArrayList<>();
        for (Showtime showtime : cineplex.getShowtimes())
            if (showtime.getMovie().equals(movie))
                showtimes.add(showtime);
        return showtimes;
    }

    // TODO Javadoc
    public List<Showtime> findByCineplexAndCinema(UUID cineplexId, UUID cinemaId) {
        CineplexController cineplexController = CineplexController.getInstance();
        Cineplex cineplex = cineplexController.findById(cineplexId);

        return cineplex.getShowtimes().stream().filter(showtime ->
                showtime.getCinema().getId().equals(cinemaId)).collect(Collectors.toList());
    }

    /**
     * Checks whether this ticket type is available for a booking.
     *
     * @param showtimeId the ID of the showtime to be checked for.
     * @return true if the ticket type is avaible for the cinema of this showtime.
     */
    public List<TicketType> getAvailableTicketTypes(UUID showtimeId) {
        Showtime showtime = findById(showtimeId);
        Cinema cinema = showtime.getCinema();

        Date showtimeDate = showtime.getStartTime();
        List<TicketType> availableTicketTypes = cinema.getType().getTicketTypes();
        if (HolidayConfig.isHoliday(showtimeDate) ||
                Utilities.dateFallsOn(showtimeDate, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY) &&
                        availableTicketTypes.contains(TicketType.PEAK))
            availableTicketTypes = Arrays.asList(TicketType.PEAK);
        else
            availableTicketTypes.remove(TicketType.PEAK);

        return availableTicketTypes;
    }
}
