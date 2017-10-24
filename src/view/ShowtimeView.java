package view;

import model.booking.Showtime;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

import java.util.Arrays;

public class ShowtimeView extends View {
    public ShowtimeView(Showtime showtime) {
        setTitle((showtime.isNoFreePasses() ? "*" : "") + new MovieView(showtime.getMovie()).getTitle());
        setContent("Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)),
                "Screening Time: " + Utilities.toFormat(showtime.getStartTime(), "HH:mm")
                );
    }
}
