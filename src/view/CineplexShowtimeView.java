package view;

import manager.MovieController;
import model.booking.Showtime;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;
import view.ui.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CineplexShowtimeView extends View {

    MovieController movieController = MovieController.getInstance();

    public CineplexShowtimeView(Cineplex cineplex, Date dateFilter, MovieStatus movieStatusFilter) {
        setTitle(cineplex.getName());
        ArrayList<String> content = new ArrayList<>();
        List<Movie> movies = movieController.findByCineplex(cineplex);
        for (Movie movie : movieStatusFilter != null ? (movies.stream().filter(movie ->
                movie.getStatus() == movieStatusFilter).collect(Collectors.toList())) : movies) {
            List<Showtime> movieShowtimes = Arrays.asList(movie.getShowtimes());
            if (dateFilter != null)
                movieShowtimes = movieShowtimes.stream().filter(showtime ->
                        Utilities.getStartOfDate(showtime.getStartTime())
                                .compareTo(Utilities.getStartOfDate(dateFilter)) == 0).collect(Collectors.toList());
            if (movieShowtimes.size() > 0)
                content.add(new MovieShowtimeView(movie, movieShowtimes)
                        .flatten(" : ", " | "));
        }
        setContent(content.toArray(new String[content.size()]));
    }

//    public CineplexShowtimeView(Cineplex cineplex, Movie movie) {
//        MovieController movieController = MovieController.getInstance();
//        setTitle(cineplex.getName());
//        for (Movie movie : movieController.findByCineplex(cineplex)) {
//            new MovieShowtimeView(movie)
//        }
//    }
}
