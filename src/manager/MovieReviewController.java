package manager;

import exception.InvalidReviewRatingException;
import exception.UninitialisedSingletonException;
import model.commons.User;
import model.movie.Movie;
import model.movie.MovieReview;

import java.util.UUID;

public class MovieReviewController extends EntityController<MovieReview> {

    private static MovieReviewController instance;

    private MovieReviewController() {
        super();
    }

    public static void init() {
        instance = new MovieReviewController();
    }

    public static MovieReviewController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    public MovieReview createReview(String review, int rating, UUID movieId, UUID authorId) throws InvalidReviewRatingException {

        MovieController movieManager = MovieController.getInstance();
        UserController userManager = UserController.getInstance();

        if (rating > 5 || rating < 0)
            throw new InvalidReviewRatingException();

        Movie movie = movieManager.findById(movieId);
        User author = userManager.findById(authorId);
        MovieReview movieReview = new MovieReview(review, rating, author);
        entities.put(movieReview.getId(), movieReview);
        movie.addReview(movieReview);
        author.addReview(movieReview);

        return movieReview;
    }

}
