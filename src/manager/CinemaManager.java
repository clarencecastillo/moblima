package manager;

import java.util.UUID;
import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;

public class CinemaManager extends EntityManager<Cinema> {

    private static CinemaManager instance;

    private CinemaManager() {
        super();
    }

    public static CinemaManager getInstance() {
        if (instance == null)
            instance = new CinemaManager();
        return instance;
    }

    public Cinema createCinema(UUID cineplexId, String code,
                               CinemaType type, CinemaLayout layout) {

        CineplexManager cineplexManager = CineplexManager.getInstance();
        Cineplex cineplex = cineplexManager.findById(cineplexId);
        Cinema cinema = new Cinema(code, cineplex, type, layout);

        cineplex.addCinema(cinema);
        entities.put(cinema.getId(), cinema);
        return cinema;
    }
}
