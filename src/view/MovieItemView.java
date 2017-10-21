package view;

import java.util.Arrays;
import model.movie.Movie;
import view.ui.ViewItem;

public class MovieItemView extends ViewItem {

    public MovieItemView(Movie movie) {
        super(movie.getId().toString());

        setTitle(String.format("%s [%s] %s", movie.getTitle(),
                                         movie.getType(), movie.getRating()));
        setContent(new String[] {
            "Director: " + movie.getDirector(),
            "Actors: " + String.join(",", Arrays.stream(movie.getActors())
                                                .map(String::valueOf).toArray(String[]::new)),
            "Runtime: " + movie.getRuntimeMinutes(),
            "Score: " + (movie.getOverallReviewRating() == -1 ? "NA" :
                         String.format("%.1f/5.0", movie.getOverallReviewRating())),
            " ",
            "Sypnosis",
            "--------",
            movie.getSypnosis()
        });
    }
}
