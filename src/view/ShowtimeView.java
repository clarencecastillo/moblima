package view;

import manager.MovieController;
import model.booking.Showtime;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;
import view.ui.View;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ShowtimeView extends View {

    MovieController movieController = MovieController.getInstance();

    public ShowtimeView(Showtime showtime) {
        setTitle(Utilities.toFormat(showtime.getStartTime(), "hh:mm a"));
        setContent("Cinema: " + showtime.getCineplex().getName() + " Hall " + showtime.getCinema().getCode(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)),
                "Free Seating Allowed: " + (showtime.isNoFreePasses() ? "No" : "Yes")
        );
    }

    public ShowtimeView(Cineplex cineplexFilter, Movie movie, Date dateFilter) {

        List<Showtime> movieShowtimes = movie.getShowtimes();
        if (dateFilter != null)
            movieShowtimes = movieShowtimes.stream().filter(showtime ->
                    Utilities.getStartOfDate(showtime.getStartTime())
                            .compareTo(Utilities.getStartOfDate(dateFilter)) == 0).collect(Collectors.toList());

        if (cineplexFilter != null)
            movieShowtimes = movieShowtimes.stream().filter(showtime ->
                    showtime.getCineplex().equals(cineplexFilter)).collect(Collectors.toList());

        setTitle(new MovieView(movie).getTitle());
        setContent(movieShowtimes.size() > 0 ? (movieShowtimes.stream().map(showtime ->
                Utilities.toFormat(showtime.getStartTime(), "[hh:mm a]")).toArray(String[]::new)) :
                new String[]{"No available showtime screenings for this movie"});
    }

    public ShowtimeView(Cineplex cineplex, MovieStatus movieStatusFilter, Date dateFilter) {
        List<Movie> movies = movieController.findByCineplex(cineplex.getId());

        if (movieStatusFilter != null)
            movies = movies.stream().filter(movie ->
                    movie.getStatus() == movieStatusFilter).collect(Collectors.toList());

        setTitle(cineplex.getName());
        setContent(movies.stream().map(movie ->
                new ShowtimeView(cineplex, movie, dateFilter)
                        .flatten(" : ", " ")).toArray(String[]::new));
    }
}
