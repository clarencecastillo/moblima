package view;

import model.booking.Showtime;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.Arrays;

public class ShowtimeView extends View {
    public ShowtimeView(Showtime showtime) {
        setTitle(Utilities.toFormat(showtime.getStartTime(), "hh:mm a"));
        setContent("Cinema: " + showtime.getCineplex().getName() + " Hall " + showtime.getCinema().getCode(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)),
                "Free Seating Allowed: " + (showtime.isNoFreePasses() ? "No" : "Yes")
                );
    }
}
