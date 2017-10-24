package model.movie;

import java.util.Date;
import java.util.UUID;
import model.commons.Entity;
import model.commons.User;
/**
 Represents review of a movie written by a user who is a moviegoer.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
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
     * Creates the review with the content, rating and author.
     * The date create will be the current date.
     * @param review The content of this review.
     * @param rating The rating of this review.
     * @param author The author of this review.
     */
    public MovieReview(String review, int rating, User author) {
        this.review = review;
        this.rating = rating;
        this.created = new Date();
        this.author = author;
    }

    /**
     * Gets the review of this movie.
     * @return the review of this movie.
     */
    public String getReview() {
        return review;
    }

    /**
     * Gets the rating of this movie.
     * @return the rating of this movie.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the created day of this movie.
     * @return the created day of this movie.
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Gets the author of this movie.
     * @return the author of this movie.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Changes the content of this review.
     * @param review the new content of this review.
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Changes the rating of this review.
     * @param rating the new rating of this review.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Changes the author of this review.
     * @param author the new author of this review.
     */
    public void setAuthor(User author) {
        this.author = author;
    }
}
