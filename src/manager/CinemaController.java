package manager;

import java.util.UUID;
import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;

public class CinemaController extends EntityController<Cinema> {

    private static CinemaController instance = new CinemaController();

    private CinemaController() {
        super();
    }

    public static CinemaController getInstance() {
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
}
