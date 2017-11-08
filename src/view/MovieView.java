package view;

import model.booking.Showtime;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This view displays the user interface for the user to view movie details.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class MovieView extends View {


    public MovieView(Movie movie, boolean showScore, boolean showSales) {
        double rating = movie.getOverallReviewRating();

        setTitle(movie.toString());

        ArrayList<String> content = new ArrayList();
        content.add("Director: " + movie.getDirector());
        content.add("Actors: " + String.join(",",
                movie.getActors().stream().map(String::valueOf).collect(Collectors.toList())));
        content.add("Runtime: " + movie.getRuntimeMinutes() + " minutes");

        if (showScore)
            content.add("Score: " + (rating == -1 ? "NA" : String.format("%.1f/5.0", rating)));

        if (showSales) {
            content.add("Gross Sales: " + String.format("$%.2f", movie.getGrossSales()));
            content.add("Weekend Gross Sales: " + String.format("$%.2f", movie.getWeekendGrossSales()));
        }

        content.add(" ");
        content.add("Sypnosis");
        content.add(movie.getSynopsis());
        setContent(content);
    }

    public MovieView(Movie movie, List<Showtime> showtimes) {
        setTitle(movie.toString());
        if (showtimes.size() > 0) {
            Collections.sort(showtimes);
            setContent(showtimes.stream().map(showtime ->
                    Utilities.toFormat(showtime.getStartTime(), "[hh:mm a]")).toArray(String[]::new));
        } else
            setContent("No available showtime screenings for this movie.");

    }
}
