package view;

import model.booking.Showtime;
import util.Utilities;
import view.ui.View;

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
