package model.cinema;

import java.util.*;

import model.booking.Showtime;
import model.commons.Entity;
import model.movie.Movie;

/**
 Represents a cineplex
 Cineplexes are in different locations, managed by the vendor.
 Each Cineplex has 3 or more cinemas.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class Cineplex extends Entity {

    /**
     * This cinemplex's name.
     */
    private String name;

    /**
     * This cineplex's address.
     */
    private String address;

    /**
     * This cineplex's array list of cinemas.
     */
    private ArrayList<Cinema> cinemas;

    /**
     * This cineplex's array list of showtimes.
     */
    private ArrayList<Showtime> showtimes;

    /**
     * Creates a cineplex with the given name and address.
     * @param name This cinemplex's name.
     * @param address This cineplex's address.
     */
    public Cineplex(String name, String address) {
        this.name = name;
        this.cinemas = new ArrayList<>();
        this.address = address;
        this.showtimes = new ArrayList<>();
    }

    /**
     * Gets this cinemplex's name.
     * @return this cinemplex's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this cinemplex's address.
     * @return this cinemplex's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets this cinemplex's list of cinemas.
     * @return this cinemplex's list of cinemas.
     */
    public List<Cinema> getCinemas() {
        return cinemas;
    }

    /**
     * Gets this cinemplex's list of showtimes.
     * @return this cinemplex's list of showtimes.
     */
    public List<Showtime> getShowtimes() { return showtimes; }

    /**
     * Changes this cinemplex's name.
     * @param name This cinemplex's new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes this cinemplex's address.
     * @param address This cinemplex's new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Adds a new cinema to this cineplex.
     * @param cinema This cinemplex's new cinema.
     */
    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    /**
     * Adds a new showtime to this cineplex.
     * @param showtime This cinemplex's new showtime.
     */
    public void addShowtime (Showtime showtime) {showtimes.add(showtime); }

    /**
     * Removes a cinema from this cineplex.
     * @param cinema Thie cinema to be removed from this cineplex.
     */
    public void removeCinema(Cinema cinema) {
        cinemas.remove(cinema);
    }

    /**
     * Removes a showtime from this cineplex.
     * @param showtime Thie showtime to be removed from this cineplex.
     */
    public void removeShowtime(Showtime showtime) { showtimes.remove(showtime); }

//    public List<Movie> getMovie() {
//        Set<Movie> movies = new HashSet<Movie>();
//        for (Showtime showtime:showtimes)
//            movies.add(showtime.getMovie());
//        List movieList = new ArrayList(movies);
//        return movieList;
//    }
}
