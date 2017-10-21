package config;

import java.util.Arrays;
import java.util.Hashtable;
import model.booking.TicketType;
import model.cinema.CinemaType;
import model.cinema.Seat;
import model.cinema.SeatType;
import model.movie.MovieType;
import model.transaction.Priceable;

public class TicketConfig implements Configurable {

    private static TicketConfig instance = new TicketConfig();
    private static Hashtable<CinemaType, TicketType[]> cinemaTicketTypes;
    private static Hashtable<Priceable, Double> priceableRates;

    private TicketConfig() {
        reset();
    }

    public static TicketConfig getInstance() {
        return instance;
    }

    public static TicketType[] getAvailableTicketTypes(CinemaType cinemaType) {
        return cinemaTicketTypes.get(cinemaType);
    }

    public static boolean isAvailable(CinemaType cinemaType, TicketType ticketType) {
        return Arrays.asList(getAvailableTicketTypes(cinemaType)).contains(ticketType);
    }

    public static double getPriceableRate(Priceable priceable) {
        return priceableRates.get(priceable);
    }

    public void setPriceableRate(Priceable priceable, double price) {
        priceableRates.put(priceable, price);
    }

    public void setAvailableTicketTypes(CinemaType cinemaType, TicketType[] ticketTypes) {
        cinemaTicketTypes.put(cinemaType, ticketTypes);
    }

    @Override
    public ConfigType getConfigType() {
        return ConfigType.TICKET;
    }

    @Override
    public void reset() {

        cinemaTicketTypes = new Hashtable<CinemaType, TicketType[]>();
        priceableRates = new Hashtable<Priceable, Double>();

        // DEFAULT AVAILABLE TICKET TYPES
        TicketType[] regularTicketTypes = {
            TicketType.PEAK, TicketType.SENIOR_CITIZEN,
            TicketType.STANDARD, TicketType.STUDENT};
        TicketType[] platinumTicketTypes = {TicketType.STANDARD};
        TicketType[] eliteTicketTypes = {TicketType.STANDARD};

        cinemaTicketTypes.put(CinemaType.REGULAR, regularTicketTypes);
        cinemaTicketTypes.put(CinemaType.PLATINUM, platinumTicketTypes);
        cinemaTicketTypes.put(CinemaType.EXECUTIVE, eliteTicketTypes);

        // DEFAULT MOVIE TYPE SURCHARGE RATES
        priceableRates.put(MovieType.TWO_DIMENSIOM, 0.0);
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

        // DEFAULT SEAT TYPE SURCHARGE RATES
        priceableRates.put(SeatType.SINGLE, 0.0);
        priceableRates.put(SeatType.COUPLE, 0.0);
        priceableRates.put(SeatType.HANDICAP, 0.0);

    }
}
