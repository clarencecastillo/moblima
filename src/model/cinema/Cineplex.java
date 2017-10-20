package model.cinema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import model.commons.Entity;

// Cineplexes are in different locations, managed by the vendor.
// Each Cineplex will have 3 or more cinemas. (composition)
public class Cineplex extends Entity {

    private String name;
    private String address;
    private ArrayList<Cinema> cinemas;

    public Cineplex(String name, String address) {
        this.name = name;
        this.cinemas = new ArrayList<Cinema>();
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    public void removeCinema(Cinema cinema) {
        cinemas.remove(cinema);
    }
}
