package manager;

import model.cinema.Cineplex;

public class CineplexManager extends EntityManager<Cineplex> {

    private static CineplexManager instance;

    private CineplexManager() {
        super();
    }

    public static CineplexManager getInstance() {
        if (instance == null)
            instance = new CineplexManager();
        return instance;
    }
}
