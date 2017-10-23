package view;

import model.booking.Showtime;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.Arrays;
import java.util.List;

public class MovieShowtimeView extends View {

//    ShowtimeController showtimeController = ShowtimeController.getInstance();

    public MovieShowtimeView(Movie movie, List<Showtime> showtimes) {
        setTitle(new MovieView(movie).getTitle());
        setContent(showtimes.stream().map(showtime ->
                Utilities.toFormat(showtime.getStartTime(), "HH:mm")).toArray(String[]::new));
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
