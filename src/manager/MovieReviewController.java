package manager;

import exception.InvalidReviewRatingException;
import exception.UninitialisedSingletonException;
import model.commons.User;
import model.movie.Movie;
import model.movie.MovieReview;

import java.util.UUID;

/**
 Represents the controller of movie reviews.
 @author Castillo Clarence Fitzgerald Gumtang
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
     * @throws InvalidReviewRatingException if the review rating is not within 0 to 5.
     */
    public MovieReview createReview(String review, int rating, UUID movieId, UUID authorId)
            throws InvalidReviewRatingException {

        MovieController movieManager = MovieController.getInstance();
        UserController userManager = UserController.getInstance();

        if (rating > 5 || rating < 0)
            throw new InvalidReviewRatingException();

        Movie movie = movieManager.findById(movieId);
        User author = userManager.findById(authorId);
        MovieReview movieReview = new MovieReview(review, movie, rating, author);
        entities.put(movieReview.getId(), movieReview);
        movie.addReview(movieReview);
        author.addReview(movieReview);

        return movieReview;
    }

    // TODO Javadoc
    public void removeReview(UUID movieReviewId) {

        // TODO validate if movieReviewId exists and throw appropriate exception

        MovieReview movieReview = findById(movieReviewId);
        movieReview.getMovie().removeReview(movieReview);
        movieReview.getAuthor().removeReview(movieReview);
        entities.remove(movieReview.getId());
    }

}
