package view;

import model.movie.MovieReview;
import util.Utilities;
import view.ui.View;

/**
 * This view displays the user interface of one movie review.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class MovieReviewView extends View {

    public MovieReviewView(MovieReview movieReview) {
        setTitle(movieReview.getAuthor().getFullName() + " " +
                Utilities.toFormat(movieReview.getCreated(), "dd-MM-YYYY HH:mm"));
        setContent(
                "Rating: " + movieReview.getRating() + "/5",
                movieReview.getReview());
    }
}
