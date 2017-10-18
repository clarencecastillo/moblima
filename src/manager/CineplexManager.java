package manager;

import model.cinema.Cinema;
import model.cinema.CinemaLayout;
import model.cinema.CinemaType;
import model.cinema.Cineplex;

import java.util.UUID;

public class CineplexManager extends EntityManager<Cineplex> {

    private static CineplexManager instance = new CineplexManager();

    private CineplexManager() {
        super();
    }

    public static CineplexManager getInstance() {
        return instance;
    }

    public Cineplex createCineplex(String name, String address) {
        Cineplex cineplex = new Cineplex(name, address);
        entities.put(cineplex.getId(), cineplex);
        return cineplex;
    }




}
