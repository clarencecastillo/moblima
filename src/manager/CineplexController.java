package manager;

import model.cinema.Cineplex;

public class CineplexController extends EntityController<Cineplex> {

    private static CineplexController instance = new CineplexController();

    private CineplexController() {
        super();
    }

    public static CineplexController getInstance() {
        return instance;
    }

    public Cineplex createCineplex(String name, String address) {
        Cineplex cineplex = new Cineplex(name, address);
        entities.put(cineplex.getId(), cineplex);
        return cineplex;
    }




}
