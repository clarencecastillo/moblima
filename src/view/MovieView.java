package view;

import java.util.Arrays;
import model.movie.Movie;
import view.ui.View;

public class MovieView extends View {

    private Movie movie;

    public MovieView(Movie movie) {
        this.movie = movie;

        setTitle(String.format("%s [%s] %s", movie.getTitle(),
                               movie.getType(), movie.getRating()));
        setContent("Status: " + movie.getStatus().toString(),
                "Director: " + movie.getDirector(),
                "Actors: " + String.join(",",
                        Arrays.stream(movie.getActors())
                                .map(String::valueOf).toArray(String[]::new)),
                "Runtime: " + movie.getRuntimeMinutes() + " minutes",
                "Score: " + (movie.getOverallReviewRating() == -1 ? "NA" :
                        String.format("%.1f/5.0", movie.getOverallReviewRating())),
                " ",
                "Sypnosis",
                "--------",
                movie.getSypnosis());
    }
}
