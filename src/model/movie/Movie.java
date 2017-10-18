package model.movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import model.booking.Booking;
import model.booking.Showtime;
import model.booking.Ticket;
import model.booking.TicketStatus;
import model.commons.Entity;
import model.commons.Searchable;

public class Movie extends Entity implements Searchable {

    private String title;
    private String sypnosis;
    private MoviePerson director;
    private MovieType type;
    private ArrayList<MoviePerson> actors;
    private ArrayList<MovieReview> reviews;
    private ArrayList<Showtime> showtimes;
    private MovieStatus status;
    private MovieRating rating;

    public Movie(String title, String sypnosis, MoviePerson director, MovieType type,
                 MoviePerson[] actors, MovieStatus status, MovieRating rating) {
        this.title = title;
        this.sypnosis = sypnosis;
        this.director = director;
        this.actors = new ArrayList<MoviePerson>(Arrays.asList(actors));
        this.type = type;
        this.reviews = new ArrayList<MovieReview>();
        this.showtimes = new ArrayList<Showtime>();
        this.status = status;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getSypnosis() {
        return sypnosis;
    }

    public MoviePerson getDirector() {
        return director;
    }

    public MoviePerson[] getActors() {
        return actors.toArray(new MoviePerson[actors.size()]);
    }

    public MovieType getType() {
        return type;
    }

    public MovieReview[] getReviews() {
        return reviews.toArray(new MovieReview[reviews.size()]);
    }

    public Showtime[] getShowtimes() {
        return showtimes.toArray(new Showtime[showtimes.size()]);
    }

    public MovieStatus getStatus() {
        return status;
    }

    public MovieRating getRating() {
        return rating;
    }

    public double getOverallReviewRating() {
        if (reviews.size() <= 1)
            return -1;

        int sum = 0;
        for(MovieReview review : reviews)
            sum += review.getRating();
        return sum / reviews.size();
    }

    public int getTicketSales() {
        int sum = 0;
        for(Showtime showtime: showtimes)
            for(Booking booking: showtime.getBookings())
                for(Ticket ticket:booking.getTickets())
                    if (ticket.getStatus() == TicketStatus.VALID) sum++;
        return sum;
    }

    public String[] getSearchTags() {

        ArrayList<String> tags = new ArrayList<>();

        // Movie Title
        tags.add(title);

        // Actors
        for (MoviePerson moviePerson: actors) {
            tags.add(moviePerson.getFirstName());
            tags.add(moviePerson.getLastName());
            tags.add(moviePerson.getFullName());
        }

        // Director
        tags.add(director.getFirstName());
        tags.add(director.getLastName());
        tags.add(director.getLastName());

        return tags.toArray(new String[tags.size()]);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSypnosis(String sypnosis) {
        this.sypnosis = sypnosis;
    }

    public void setDirector(MoviePerson director) {
        this.director = director;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public void setActors(MoviePerson[] actors) {
        this.actors = new ArrayList<MoviePerson>(Arrays.asList(actors));
    }

    public void addReview(MovieReview movieReview) {
        reviews.add(movieReview);
    }

    public void removeReview(MovieReview movieReview) {
        reviews.remove(movieReview);
    }

    public void addShowtime(Showtime showtime) {
        showtimes.add(showtime);
    }

    public void removeShowtime(Showtime showtime) {
        showtimes.remove(showtime);
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }
}
