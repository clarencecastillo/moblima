package view;

import model.booking.Booking;
import view.ui.View;

public class BookingView extends View {

    private Booking booking;

    public BookingView(Booking booking) {
        this.booking = booking;

        setTitle(booking.getShowtime().getMovie().toString());
        setContent("Cinema: " + booking.getShowtime().getCinema().getCode(),
                "Start Time: " + booking.getShowtime().getStartTime(),
                "Seats: " + String.join(",",
                        booking.getSeats().stream().map(String::valueOf).toArray(String[]::new))
        );
    }
}
