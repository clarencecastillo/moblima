package view;

import java.util.Arrays;
import model.booking.Booking;
import model.booking.Ticket;
import view.ui.View;

public class BookingView extends View {

    private Booking booking;

    public BookingView(Booking booking) {
        this.booking = booking;

        setTitle(new MovieView(booking.getShowtime().getMovie()).getTitle());
        setContent( "Cinema: " + booking.getShowtime().getCinema().getCode(),
                    "Start Time: " + booking.getShowtime().getStartTime(),
                    "Seats: " + String.join(",",
                                            booking.getTickets().stream()
                                                  .map(Ticket::getSeat).map(String::valueOf)
                                                  .toArray(String[]::new)));
    }
}
