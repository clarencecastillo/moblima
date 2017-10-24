package view;

import model.booking.Showtime;
import model.cinema.Cineplex;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MovieShowtimeView extends View {

//    ShowtimeController showtimeController = ShowtimeController.getInstance();

    public MovieShowtimeView(Movie movie, Cineplex cineplexFilter, Date dateFilter) {

        List<Showtime> movieShowtimes = Arrays.asList(movie.getShowtimes());
        if (dateFilter != null)
            movieShowtimes = movieShowtimes.stream().filter(showtime ->
                    Utilities.getStartOfDate(showtime.getStartTime())
                            .compareTo(Utilities.getStartOfDate(dateFilter)) == 0).collect(Collectors.toList());

        if (cineplexFilter != null)
            movieShowtimes = movieShowtimes.stream().filter(showtime ->
                showtime.getCineplex().equals(cineplexFilter)).collect(Collectors.toList());

        setTitle(new MovieView(movie).getTitle());
        setContent(movieShowtimes.size() > 0 ? (movieShowtimes.stream().map(showtime ->
                Utilities.toFormat(showtime.getStartTime(), "HH:mm")).toArray(String[]::new)) :
                new String[] { "No available showtime screenings for this movie"});
    }

//    public MovieShowtimeView(Movie movie, Cineplex cineplex) {
//        ShowtimeController showtimeController = ShowtimeController.getInstance();
//        setTitle(new MovieView(movie).getTitle());
//        for (Showtime showtime : movie.getShowtimes())
//            setContent(String.join(" | ", showtimes.stream().map(showtime ->
//                    Utilities.toFormat(showtime.getStartTime(), "HH:mm")).toArray(String[]::new)));
//    }
//
//    public MovieShowtimeView(Movie movie, Cineplex cineplex, Date date) {
//        ShowtimeController showtimeController = ShowtimeController.getInstance();
//        setTitle(new MovieView(movie).getTitle());
//        for (Showtime showtime : movie.getShowtimes())
//            setContent(String.join(" | ", showtimes.stream().map(showtime ->
//                    Utilities.toFormat(showtime.getStartTime(), "HH:mm")).toArray(String[]::new)));
//    }
//
//    public MovieShowtimeView(Movie movie, Date date) {
//        ShowtimeController showtimeController = ShowtimeController.getInstance();
//        setTitle(new MovieView(movie).getTitle());
//        for (Showtime showtime : movie.getShowtimes())
//            setContent(String.join(" | ", showtimes.stream().map(showtime ->
//                    Utilities.toFormat(showtime.getStartTime(), "HH:mm")).toArray(String[]::new)));
//    }
}
