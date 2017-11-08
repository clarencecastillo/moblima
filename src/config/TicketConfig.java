package moblima.config;

import moblima.model.booking.TicketType;
import moblima.model.cineplex.CinemaType;
import moblima.model.movie.MovieType;
import moblima.model.transaction.Priceable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * Represents the configuration of ticket setting. It consists os the available ticket types for each cineplex type and
 * the pricing rate of each priceable, including cineplex type, movie type and ticket type.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class TicketConfig implements Configurable {

    /**
     * A reference to this singleton instance.
     */
    private static TicketConfig instance = new TicketConfig();

    /**
     * A hash table of setting with the cineplex type as the key and the array list of available ticket types as the value.
     */
    private static Hashtable<CinemaType, ArrayList<TicketType>> cinemaTicketTypes;

    /**
     * A hash table of setting with the priceable as the key and its pricing rate as the value.
     */
    private static Hashtable<Priceable, Double> priceableRates;

    /**
     * Initializes the ticket configuration by resetting.
     */
    private TicketConfig() {
        reset();
    }

    /**
     * Gets this TicketConfig's singleton instance.
     *
     * @return this BookingConfig's singleton instance.
     */
    public static TicketConfig getInstance() {
        return instance;
    }

    /**
     * Gets the available ticket types of a given cineplex type.
     *
     * @param cinemaType The cineplex type whose available ticket type is to be checked.
     * @return the available ticket types of a given cineplex type.
     */
    public static List<TicketType> getAvailableTicketTypes(CinemaType cinemaType) {
        return new ArrayList<>(cinemaTicketTypes.get(cinemaType));
    }

    /**
     * Checked whether a ticket type is available for a cineplex type.
     * @param cinemaType the cineplex type to be checked.
     * @param ticketType the ticket type to be checked.
     * @return true if this ticket type is available for this cineplex type.
     */
    public static boolean isAvailable(CinemaType cinemaType, TicketType ticketType) {
        return getAvailableTicketTypes(cinemaType).contains(ticketType);
    }

    /**
     * Gets the pricing rate of a given priceable.
     * @param priceable The priceable whose pricing rate is to be returned.
     * @return pricing rate of this priceable.
     */
    public static double getPriceableRate(Priceable priceable) {
        return priceableRates.get(priceable);
    }

    /**
     * Changes the pricing rate of a given priceable.
     * @param priceable The priceable whose pricing rate is to be changed.
     * @param price The new pricing rate of this priceable.
     */
    public void setPriceableRate(Priceable priceable, double price) {
        priceableRates.put(priceable, price);
    }

    /**
     * Changes the available ticket types of a given cineplex type.
     * @param cinemaType The type of the cineplex to be changes.
     * @param ticketTypes The new available ticket types of this cineplex.
     */
    public void setAvailableTicketTypes(CinemaType cinemaType, List<TicketType> ticketTypes) {
        cinemaTicketTypes.put(cinemaType, new ArrayList<>(ticketTypes));
    }

    /**
     * Gets the type of this configuration.
     * @return the type of this configuration which is ticket.
     */
    @Override
    public ConfigType getConfigType() {
        return ConfigType.TICKET;
    }

    /**
     * Resets the ticket settings to the default.
     */
    @Override
    public void reset() {

        cinemaTicketTypes = new Hashtable<>();
        priceableRates = new Hashtable<>();

        // DEFAULT AVAILABLE TICKET TYPES
        cinemaTicketTypes.put(CinemaType.REGULAR,
                new ArrayList<>(Arrays.asList(
                        TicketType.PEAK,
                        TicketType.SENIOR_CITIZEN,
                        TicketType.STANDARD,
                        TicketType.STUDENT)));
        cinemaTicketTypes.put(CinemaType.PLATINUM,
                new ArrayList<>(Arrays.asList(
                        TicketType.STANDARD,
                        TicketType.PEAK)));
        cinemaTicketTypes.put(CinemaType.EXECUTIVE,
                new ArrayList<>(Arrays.asList(
                        TicketType.STANDARD,
                        TicketType.PEAK)));

        // DEFAULT MOVIE TYPE SURCHARGE RATES
        priceableRates.put(MovieType.TWO_DIMENSION, 0.0);
        priceableRates.put(MovieType.THREE_DIMENSION, 1.0);
        priceableRates.put(MovieType.BLOCKBUSTER, 1.0);

        // DEFAULT TICKET TYPE RATES
        priceableRates.put(TicketType.PEAK, 13.0);
        priceableRates.put(TicketType.SENIOR_CITIZEN, 5.0);
        priceableRates.put(TicketType.STANDARD, 9.0);
        priceableRates.put(TicketType.STUDENT, 7.0);

        // DEFAULT CINEMA TYPE SURCHARGE RATES
        priceableRates.put(CinemaType.REGULAR, 0.0);
        priceableRates.put(CinemaType.PLATINUM, 5.0);
        priceableRates.put(CinemaType.EXECUTIVE, 7.0);
    }
}
