package view;

import model.booking.Showtime;
import model.booking.TicketType;
import model.cineplex.Cinema;
import model.cineplex.Cineplex;
import model.cineplex.Seat;
import model.movie.Movie;
import util.Utilities;
import view.ui.View;

/**
 * This moblima.view displays the user interface for the user to moblima.view tickets.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class TicketView extends View {

    private Showtime showtime;
    private Seat seat;
    private TicketType ticketType;

    private Movie movie;
    private Cineplex cineplex;
    private Cinema cinema;

    public TicketView(Showtime showtime, Seat seat, TicketType ticketType) {
        this.showtime = showtime;
        this.seat = seat;
        this.ticketType = ticketType;

        movie = showtime.getMovie();
        cineplex = showtime.getCineplex();
        cinema = showtime.getCinema();
    }

    @Override
    public void displayTitle() {
        System.out.println(View.line('-', VIEW_WIDTH / 2));
        System.out.println(movie.toString().toUpperCase());
        System.out.println(View.line('-', VIEW_WIDTH / 2));
    }

    @Override
    public void displayContent() {
        System.out.println(" | " + cineplex.getName().toUpperCase() + " HALL " + cinema.getCode().toUpperCase());
        System.out.println(" | DATE: " + Utilities.toFormat(showtime.getStartTime(), "EEE dd MMM YYYY"));
        System.out.println(" | TIME: " + Utilities.toFormat(showtime.getStartTime(), "HH:mm"));
        System.out.println(" | SEAT ROW: " + seat.getRow());
        System.out.println(" | SEAT COLUMN: " + seat.getColumn());
        System.out.println(" | TICKET: " + ticketType.name().toUpperCase());
        System.out.println(" | PRICE: " + String.format("$%.2f", showtime.getTicketTypePricing(ticketType)));
        System.out.println(View.line('-', VIEW_WIDTH / 2));
    }
}
