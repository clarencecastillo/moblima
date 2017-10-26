package view;

import manager.MovieController;
import model.movie.Movie;
import view.ui.View;

import java.util.stream.Collectors;

public class MovieView extends View {

    private Movie movie;

    private MovieController movieController = MovieController.getInstance();

    public MovieView(Movie movie) {
        this.movie = movie;

        setTitle(movie.toString());
        setContent("Status: " + movie.getStatus().toString(),
                "Director: " + movie.getDirector(),
                "Actors: " + String.join(",",
                        movie.getActors().stream().map(String::valueOf).collect(Collectors.toList())),
                "Runtime: " + movie.getRuntimeMinutes() + " minutes",
                "Score: " + (movieController.getOverallReviewRating(movie.getId()) == -1 ? "NA" :
                        String.format("%.1f/5.0", movieController.getOverallReviewRating(movie.getId()))),
                " ",
                "Sypnosis",
                "--------",
                movie.getSynopsis());
    }
}
