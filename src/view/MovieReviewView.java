package view;

import model.movie.MovieReview;
import util.Utilities;
import view.ui.View;

public class MovieReviewView extends View {

    public MovieReviewView(MovieReview movieReview) {
        setTitle(movieReview.getAuthor().getFullName() + " " +
                Utilities.toFormat(movieReview.getCreated(), "dd-MM-YYYY HH:mm"));
        setContent(
                "Rating: " + movieReview.getRating() + "/5",
                movieReview.getReview());
    }
}
