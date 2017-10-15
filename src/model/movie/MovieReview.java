package model.movie;

import java.util.Date;
import java.util.UUID;
import model.commons.Entity;
import model.commons.User;

public class MovieReview extends Entity {

    private String review;
    private int rating;
    private Date created;
    private Movie movie;
    private User author;

    public MovieReview(String review, int rating, Movie movie, User author) {
        this.review = review;
        this.rating = rating;
        this.created = new Date();
        this.movie = movie;
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public Date getCreated() {
        return created;
    }

    public Movie getMovie() {
        return movie;
    }

    public User getAuthor() {
        return author;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
