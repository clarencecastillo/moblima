package manager;

import exception.IllegalActionException;
import exception.UninitialisedSingletonException;
import model.booking.Booking;
import model.booking.BookingStatus;
import model.commons.User;
import model.movie.Movie;
import model.movie.MovieReview;

import java.util.UUID;

/**
 Represents the controller of movie reviews.
 @version 1.0
 @since 2017-10-20
 */
public class MovieReviewController extends EntityController<MovieReview> {

    /**
     * A reference to this singleton instance.
     */
    private static MovieReviewController instance;

    /**
     * Creates the movie review controller.
     */
    private MovieReviewController() {
        super();
    }

    /**
     * Initialize the movie review controller.
     */
    public static void init() {
        instance = new MovieReviewController();
    }

    /**
     * Gets this movie review Controller's singleton instance.
     * @return this movie review Controller's singleton instance.
     */
    public static MovieReviewController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    /**
     * Creates a movie review when a movie goer enter the review and rating. The movie review is added to the
     * movie reviews of the given movie and user. The review is put into the entities.
     * @param review The content of this movie review.
     * @param rating The rating of this movie review.
     * @param movieId The ID of the movie of which this movie review is written for.
     * @param authorId The ID of the author who enters this movie review.
     * @return the newly created movie review.
     * @throws IllegalActionException if the review rating is not within 0 to 5.
     */
    public MovieReview createReview(String review, int rating, UUID movieId, UUID authorId)
            throws IllegalActionException {

        MovieController movieManager = MovieController.getInstance();
        UserController userManager = UserController.getInstance();

        if (rating > 5 || rating < 0)
            throw new IllegalActionException("The review rating must be between 1 and 5.");

        Movie movie = movieManager.findById(movieId);
        User author = userManager.findById(authorId);

        boolean hasBookedMovie = false;
        for (Booking booking : author.getBookings()) {
            hasBookedMovie = booking.getShowtime().getMovie().equals(movie) &&
                    booking.getStatus() == BookingStatus.CONFIRMED;
            if (hasBookedMovie)
                break;
        }

        if (!hasBookedMovie)
            throw new IllegalActionException("Can only submit reviews for movies you've booked");

        for (MovieReview movieReview : author.getMovieReviews())
            if (movieReview.getAuthor().equals(author) && movieReview.getMovie().equals(movie))
                throw new IllegalActionException("Can only submit review once per movie");

        MovieReview movieReview = new MovieReview(review, movie, rating, author);
        entities.put(movieReview.getId(), movieReview);
        movie.addReview(movieReview);
        author.addReview(movieReview);

        return movieReview;
    }

    /**
     * Removes a movie review, removing is from the movie and the author.
     * @param movieReviewId The ID of the movie review to be removed.
     * @exception IllegalActionException if the moview review to be removed does not exist.
     */
    public void removeReview(UUID movieReviewId) throws IllegalActionException {

        MovieReview movieReview = findById(movieReviewId);
        if (movieReview == null)
            throw new IllegalActionException("This movie review does not exist.");
        movieReview.getMovie().removeReview(movieReview);
        movieReview.getAuthor().removeReview(movieReview);
        entities.remove(movieReview.getId());
    }

}
