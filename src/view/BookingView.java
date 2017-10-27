package view;

import model.booking.Booking;
import model.booking.Showtime;
import model.booking.TicketType;
import model.cinema.Seat;
import util.Utilities;
import view.ui.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BookingView extends View {

    public BookingView(Showtime showtime, List<Seat> seats) {
        setTitle(showtime.getMovie().toString(showtime.isNoFreePasses()));
        setContent("Showing on " + Utilities.toFormat(showtime.getStartTime(), DATE_DISPLAY_FORMAT),
                "Cinema: " + showtime.getCineplex().getName() + " Hall " + showtime.getCinema().getCode(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)),
                "Seats: " + String.join(", ",
                        seats.stream().map(String::valueOf).toArray(String[]::new)));
    }

    public BookingView(Booking booking) {
        this(booking.getShowtime(), booking.getSeats());
        content.add("Transaction ID: " + booking.getPayment().getTransactionId());
    }

    public BookingView(Showtime showtime, List<Seat> seats, Hashtable<TicketType, Integer> ticketTypeCount) {
        this(showtime, seats);
        content.add(0, getTitle());
        content.add(0, " ");

        content.addAll(Arrays.asList(
                " ",
                "Tickets",
                "Type                                    Price     Quantity"
        ));
        for (TicketType ticketType : ticketTypeCount.keySet()) {
            String ticketTypeValue = ticketType.toString();
            String ticketTypeRow = ticketTypeValue + View.line(' ',
                    40 - ticketTypeValue.length());
            String priceValue = String.format("$%.2f", showtime.getTicketTypePricing(ticketType));
            ticketTypeRow += priceValue + View.line(' ', 10 - priceValue.length());
            ticketTypeRow += ticketTypeCount.get(ticketType);
            content.add(ticketTypeRow);
        }
        setContent(content.toArray(new String[content.size()]));
    }
}
