package manager;

import config.BookingConfig;
import config.TicketConfig;
import exception.IllegalActionException;
import exception.UninitialisedSingletonException;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.booking.TicketType;
import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;
import util.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 Represents the controller of cinemas.
 @version 1.0
 @since 2017-10-20
 */
public class CinemaController extends EntityController<Cinema> {

    /**
     * A reference to this singleton instance.
     */
    private static CinemaController instance;

    /**
     * Creates the cinema controller.
     */
    private CinemaController() {
        super();
    }

    /**
     * Initialize the cinema controller.
     */
    public static void init() {
        instance = new CinemaController();
    }

    /**
     * Gets this Cinema Controller's singleton instance.
     * @return this Cinema Controller's singleton instance.
     */
    public static CinemaController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    /**
     * Creates a cinema with the ID of its cineplex, code, cinema type and layout.
     * @param cineplexId The ID of the cineplex that the cinem belongs to.
     * @param code The code of this cinema.
     * @param type The type of this cinema.
     * @param layout The layout of this cinema.
     * @return a newly created cinema with the given ID of cineplex, code, cinema type and layout.
     */
    public Cinema createCinema(UUID cineplexId, String code,
                               CinemaType type, CinemaLayout layout) {

        CineplexController cineplexController = CineplexController.getInstance();
        Cineplex cineplex = cineplexController.findById(cineplexId);
        Cinema cinema = new Cinema(code, type, layout);

        cineplex.addCinema(cinema);
        entities.put(cinema.getId(), cinema);
        return cinema;
    }

    public boolean isAvaiableOn(UUID cineplexId, UUID cinemaId, Date startTime, Date endTime) {

        ShowtimeController showtimeController = ShowtimeController.getInstance();
        List<Showtime> cinemaShowtime = showtimeController.findByCineplexAndCinema(cineplexId, cinemaId);

        for (Showtime showtime : cinemaShowtime) {
            if (showtime.getStatus() != ShowtimeStatus.CANCELLED) {
                Date showtimeEndTime = Utilities.getDateAfter(showtime.getStartTime(), Calendar.MINUTE,
                        showtime.getMovie().getRuntimeMinutes() + BookingConfig.getBufferMinutesAfterShowtime());
                if (Utilities.overlaps(showtime.getStartTime(), showtimeEndTime, startTime, endTime))
                    return false;
            }
        }

        return true;
    }
}
