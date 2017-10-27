package manager;

import config.TicketConfig;
import exception.UninitialisedSingletonException;
import model.booking.TicketType;
import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;

import java.util.UUID;

/**
 Represents the controller of cinemas.
 @author Castillo Clarence Fitzgerald Gumtang
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

    /**
     * Checks whether a ticket type is available in this cinema.
     *
     * @param cinemaId The ID of the cinema to be checked.
     * @param type The ticket type to be checked
     * @return true if this ticket type is available in this cinema.
     */
    public boolean isAvailable(UUID cinemaId, TicketType type) {
        Cinema cinema = findById(cinemaId);
        return TicketConfig.isAvailable(cinema.getType(), type);
    }
}
