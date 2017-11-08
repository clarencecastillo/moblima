package moblima.controller;

import moblima.config.BookingConfig;
import moblima.exception.UninitialisedSingletonException;
import moblima.model.booking.Showtime;
import moblima.model.booking.ShowtimeStatus;
import moblima.model.cineplex.Cinema;
import moblima.model.cineplex.CinemaLayout;
import moblima.model.cineplex.CinemaType;
import moblima.model.cineplex.Cineplex;
import moblima.util.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 Represents the moblima.controller of cinemas.
 @version 1.0
 @since 2017-10-20
 */
public class CinemaController extends EntityController<Cinema> {

    /**
     * A reference to this singleton instance.
     */
    private static CinemaController instance;

    /**
     * Creates the cineplex moblima.controller.
     */
    private CinemaController() {
        super();
    }

    /**
     * Initialize the cineplex moblima.controller.
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
     * Creates a cineplex with the ID of its cineplex, code, cineplex type and layout.
     * @param cineplexId The ID of the cineplex that the cinem belongs to.
     * @param code The code of this cineplex.
     * @param type The type of this cineplex.
     * @param layout The layout of this cineplex.
     * @return a newly created cineplex with the given ID of cineplex, code, cineplex type and layout.
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
