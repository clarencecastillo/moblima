package model.movie;

import model.commons.Entity;
import model.commons.User;

import java.util.Date;

/**
 * Represents review of a movie written by a user who is a moviegoer.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class MovieReview extends Entity {

    /**
     * The content of this review.
     */
    private String review;

    /**
     * The rating of this review.
     */
    private int rating;

    /**
     * The created day of this review.
     */
    private Date created;

    /**
     * The author of this review.
     */
    private User author;

    /**
     * The movie that this review is written for.
     */
    private Movie movie;

    /**
     * Creates the review with the content, rating and author.
     * The date create will be the current date.
     *
     * @param review The content of this review.
     * @param movie The movie that this review is written for.
     * @param rating The rating of this review.
     * @param author The author of this review.
     */
    public MovieReview(String review, Movie movie, int rating, User author) {
        this.review = review;
        this.rating = rating;
        this.created = new Date();
        this.author = author;
        this.movie = movie;
    }

    /**
     * Gets the review of this movie.
     *
     * @return the review of this movie.
     */
    public String getReview() {
        return review;
    }

    /**
     * Changes the content of this review.
     *
     * @param review the new content of this review.
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Gets the rating of this movie.
     *
     * @return the rating of this movie.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Changes the rating of this review.
     *
     * @param rating the new rating of this review.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets the created day of this movie.
     *
     * @return the created day of this movie.
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Gets the author of this movie.
     *
     * @return the author of this movie.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Changes the author of this review.
     *
     * @param author the new author of this review.
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets the movie that this review is written for.
     * @return the movie that this review is written for.
     */
    public Movie getMovie() {
        return movie;
    }
}
