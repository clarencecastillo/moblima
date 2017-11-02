package view;

import model.booking.Showtime;
import util.Utilities;
import view.ui.View;

/**
 * This view displays the user interface for the user to view showtime details.
 *
 * @version 1.0
 * @since 2017-10-30
 */

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
