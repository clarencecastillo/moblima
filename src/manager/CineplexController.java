package manager;

import exception.UninitialisedSingletonException;
import model.cinema.Cineplex;

public class CineplexController extends EntityController<Cineplex> {

    private static CineplexController instance;

    private CineplexController() {
        super();
    }

    public static void init() {
        instance = new CineplexController();
    }

    public static CineplexController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    public Cineplex createCineplex(String name, String address) {
        Cineplex cineplex = new Cineplex(name, address);
        entities.put(cineplex.getId(), cineplex);
        return cineplex;
    }




}
