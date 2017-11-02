package view;

import model.booking.Showtime;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.List;
import java.util.stream.Collectors;

public class MovieView extends View {

    public MovieView(Movie movie) {
        double rating = movie.getOverallReviewRating();

        setTitle(movie.toString());
        setContent("Status: " + movie.getStatus().toString(),
                "Director: " + movie.getDirector(),
                "Actors: " + String.join(",",
                        movie.getActors().stream().map(String::valueOf).collect(Collectors.toList())),
                "Runtime: " + movie.getRuntimeMinutes() + " minutes",
                "Score: " + (rating == -1 ? "NA" : String.format("%.1f/5.0", rating)),
                " ",
                "Sypnosis",
                "--------",
                movie.getSynopsis());
    }

    public MovieView(Movie movie, List<Showtime> showtimes) {

        setTitle(movie.toString());
        setContent(showtimes.size() > 0 ? (showtimes.stream().map(showtime ->
                Utilities.toFormat(showtime.getStartTime(), "[hh:mm a]")).toArray(String[]::new)) :
                new String[]{"No available showtime screenings for this movie"});
    }
}
