package moblima.controller;

import moblima.exception.UninitialisedSingletonException;
import moblima.model.cineplex.Cineplex;
/**
 Represents the moblima.controller of cineplexes.
 @version 1.0
 @since 2017-10-20
 */
public class CineplexController extends EntityController<Cineplex> {

    /**
     * A reference to this singleton instance.
     */
    private static CineplexController instance;

    /**
     * Creates the cineplex moblima.controller.
     */
    private CineplexController() {
        super();
    }

    /**
     * Initialize the cineplex moblima.controller.
     */
    public static void init() {
        instance = new CineplexController();
    }

    /**
     * Gets this Cineplex Controller's singleton instance.
     * @return this Cineplex Controller's singleton instance.
     */
    public static CineplexController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    /**
     * Creates a cineplex with the given cineplex name and address.
     * @param name The name of the cineplex to be creates.
     * @param address The address of the cineplex to be creates.
     * @return the newly created cineplex with the given cineplex name and address.
     */
    public Cineplex createCineplex(String name, String address) {
        Cineplex cineplex = new Cineplex(name, address);
        entities.put(cineplex.getId(), cineplex);
        return cineplex;
    }
}
