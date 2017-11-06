package view;

import model.booking.Showtime;
import model.commons.Language;
import util.Utilities;
import view.ui.View;

import java.util.List;

/**
 * This view displays the user interface for the user to view showtime details.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class ShowtimeView extends View {

    public ShowtimeView(Showtime showtime) {
        setTitle(Utilities.toFormat(showtime.getStartTime(), "hh:mm a"));

        List<Language> subtitles = showtime.getSubtitles();
        setContent("Cinema: " + showtime.getCineplex().getName() + " " + showtime.getCinema().getType() +
                        " Hall " + showtime.getCinema().getCode(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + (subtitles.size() > 0 ? String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)) : "None"),
                "Free Seating Allowed: " + (showtime.isNoFreePasses() ? "No" : "Yes")
        );
    }
}
