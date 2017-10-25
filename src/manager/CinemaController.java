package manager;

import java.util.UUID;

import config.TicketConfig;
import exception.UninitialisedSingletonException;
import model.booking.TicketType;
import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;

public class CinemaController extends EntityController<Cinema> {

    private static CinemaController instance;

    private CinemaController() {
        super();
    }

    public static void init() {
        instance = new CinemaController();
    }

    public static CinemaController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    public Cinema createCinema(UUID cineplexId, String code,
                               CinemaType type, CinemaLayout layout) {

        CineplexController cineplexController = CineplexController.getInstance();
        Cineplex cineplex = cineplexController.findById(cineplexId);
        Cinema cinema = new Cinema(code, type, layout);

        cineplex.addCinema(cinema);
        entities.put(cinema.getId(), cinema);
        return cinema;
    }

    /**
     * Checks whether a ticket type is available in this cinema.
     * @param type a ticket type to be checked
     * @return true if this ticket type is available in this cinema.
     */
    public boolean isAvailable(UUID cinemaId, TicketType type) {
        Cinema cinema = findById(cinemaId);
        return TicketConfig.isAvailable(cinema.getType(), type);
    }
}
