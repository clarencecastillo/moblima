package view;

import manager.MovieController;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import view.ui.View;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CineplexShowtimeView extends View {

    MovieController movieController = MovieController.getInstance();

    public CineplexShowtimeView(Cineplex cineplex, MovieStatus movieStatusFilter, Date dateFilter) {
        List<Movie> movies = movieController.findByCineplex(cineplex.getId());

        if (movieStatusFilter != null)
            movies = movies.stream().filter(movie ->
                    movie.getStatus() == movieStatusFilter).collect(Collectors.toList());

        setTitle(cineplex.getName());
        setContent(movies.stream().map(movie ->
                new MovieShowtimeView(movie, cineplex, dateFilter)
                        .flatten(" : ", " ")).toArray(String[]::new));
    }

    public CineplexShowtimeView(Cineplex cineplex, Movie movieFilter, Date dateFilter) {
        setTitle(cineplex.getName());
        setContent(new MovieShowtimeView(movieFilter, cineplex, dateFilter).getContent());
    }

}
