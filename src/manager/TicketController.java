package manager;

import config.BookingConfig;
import exception.ExceedBookingSeatException;
import exception.IllegalShowtimeBookingException;
import exception.UnavailableBookingSeatException;
import exception.UnavailableTicketTypeException;

import java.util.UUID;

import model.booking.Booking;
import model.booking.BookingStatus;
import model.booking.SeatingStatus;
import model.booking.Showtime;
import model.booking.ShowtimeSeating;
import model.booking.Ticket;
import model.booking.TicketType;
import model.cinema.Cinema;
import model.cinema.Seat;
import model.movie.Movie;
import model.transaction.Pricing;

public class TicketController extends EntityController<Ticket> {

    private static TicketController instance = new TicketController();

    private TicketController() {
        super();
    }

    public static TicketController getInstance() {
        return instance;
    }

    public Ticket createTicket(Booking booking, Seat seat, TicketType type) throws IllegalShowtimeBookingException,
            ExceedBookingSeatException, UnavailableTicketTypeException, UnavailableBookingSeatException {

        Showtime showtime = booking.getShowtime();
        Cinema cinema = showtime.getCinema();
        ShowtimeSeating seating = showtime.getSeating();
        Movie movie = showtime.getMovie();

        // Check if booking status not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            throw new IllegalShowtimeBookingException();

        int maxSeatsPerBooking = BookingConfig.getMaxSeatsPerBooking();
        if (booking.getTickets().length == maxSeatsPerBooking)
            throw new ExceedBookingSeatException();

        // Check if ticket type is not available
        if (!type.isValidFor(booking))
            throw new UnavailableTicketTypeException();


        seating.setSeatingStatus(seat, SeatingStatus.TAKEN);
        Ticket ticket = new Ticket(seat, type, new Pricing(cinema.getType(), seat.getType(),
                                                           type, movie.getType()));
        booking.addTicket(ticket);
        return ticket;
    }
}
