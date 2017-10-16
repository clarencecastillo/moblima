package manager;

import java.util.UUID;

import manager.exception.InvalidReviewRatingException;
import model.commons.User;
import model.movie.Movie;
import model.movie.MovieReview;

public class MovieReviewManager extends EntityManager<MovieReview> {

    private static MovieReviewManager instance;

    private MovieReviewManager() {
        super();
    }

    public static MovieReviewManager getInstance() {
        if (instance == null)
            instance = new MovieReviewManager();
        return instance;
    }

    public MovieReview createReview(String review, int rating, UUID movieId, UUID authorId) throws InvalidReviewRatingException {

        MovieManager movieManager = MovieManager.getInstance();
        UserManager userManager = UserManager.getInstance();

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
