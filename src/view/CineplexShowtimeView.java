package view;

import manager.MovieController;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import view.ui.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CineplexShowtimeView extends View {

    MovieController movieController = MovieController.getInstance();

    public CineplexShowtimeView(Cineplex cineplex, MovieStatus movieStatusFilter, Date dateFilter) {
        ArrayList<String> content = new ArrayList<>();
        List<Movie> movies = movieController.findByCineplex(cineplex);

        if (movieStatusFilter != null)
            movies = movies.stream().filter(movie ->
                    movie.getStatus() == movieStatusFilter).collect(Collectors.toList());

        setTitle(cineplex.getName());
        setContent(movies.stream().map(movie ->
                new MovieShowtimeView(movie, cineplex, dateFilter)
                        .flatten(" : ", " | ")).toArray(String[]::new));
    }

    public CineplexShowtimeView(Cineplex cineplex, Movie movieFilter, Date dateFilter) {
        setTitle(cineplex.getName());
        setContent(new MovieShowtimeView(movieFilter, cineplex, dateFilter).getContent());
    }

//    public CineplexShowtimeView(Cineplex cineplex, Movie movie) {
//        MovieController movieController = MovieController.getInstance();
//        setTitle(cineplex.getName());
//        for (Movie movie : movieController.findByCineplex(cineplex)) {
//            new MovieShowtimeView(movie)
//        }
//    }
}
