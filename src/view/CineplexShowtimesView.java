package view;

import model.booking.Showtime;
import model.cinema.Cineplex;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;

public class CineplexShowtimesView extends View {

    public CineplexShowtimesView(Cineplex cineplex, Date dateFilter) {
        setTitle(cineplex.getName());
        ArrayList<String> content = new ArrayList<>();
        Hashtable<Movie, ArrayList<Showtime>> movieShowtimes = new Hashtable<>();
        for (Showtime showtime: cineplex.getShowtimes())
            if (Utilities.getStartOfDate(showtime.getStartTime())
                    .compareTo(Utilities.getStartOfDate(dateFilter)) == 0) {
                Movie showtimeMovie = showtime.getMovie();
                if (movieShowtimes.containsKey(showtimeMovie))
                    movieShowtimes.get(showtimeMovie).add(showtime);
                else
                    movieShowtimes.put(showtimeMovie, new ArrayList<>(Arrays.asList(showtime)));
            }
        for (Movie movie: movieShowtimes.keySet())
            content.add(new MovieView(movie).getTitle() + " : " + String.join(" | ",
                    movieShowtimes.get(movie).stream().map(showtime -> Utilities.toFormat(showtime.getStartTime(),
                            "HH:mm")).toArray(String[]::new)));
        setContent(content.toArray(new String[content.size()]));
    }
}
