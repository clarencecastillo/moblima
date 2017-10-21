package manager;

import java.util.UUID;

import exception.InvalidReviewRatingException;
import model.commons.User;
import model.movie.Movie;
import model.movie.MovieReview;

public class MovieReviewController extends EntityController<MovieReview> {

    private static MovieReviewController instance = new MovieReviewController();

    private MovieReviewController() {
        super();
    }

    public static MovieReviewController getInstance() {
        return instance;
    }

    public MovieReview createReview(String review, int rating, UUID movieId, UUID authorId) throws InvalidReviewRatingException {

        MovieController movieManager = MovieController.getInstance();
        UserController userManager = UserController.getInstance();

        if (rating > 5 || rating < 0)
            throw new InvalidReviewRatingException();

        Movie movie = movieManager.findById(movieId);
        User author = userManager.findById(authorId);
        MovieReview movieReview = new MovieReview(review, rating, movie, author);
        entities.put(movieReview.getId(), movieReview);
        movie.addReview(movieReview);
        author.addReview(movieReview);

        return movieReview;
    }

}