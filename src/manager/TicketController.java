package manager;

import config.BookingConfig;
import exception.ExceedBookingSeatException;
import exception.IllegalShowtimeBookingException;
import exception.IllegalTicketRemovingException;
import exception.IllegalTicketUpdatingException;
import exception.InvalidTicketStatusException;
import exception.UnavailableBookingSeatException;
import exception.UnavailableTicketTypeException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import model.booking.Booking;
import model.booking.BookingStatus;
import model.booking.SeatingStatus;
import model.booking.Showtime;
import model.booking.ShowtimeSeating;
import model.booking.Ticket;
import model.booking.TicketStatus;
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

    public Ticket createTicket(UUID bookingId, Seat seat, TicketType type) throws IllegalShowtimeBookingException,
        ExceedBookingSeatException, UnavailableTicketTypeException, UnavailableBookingSeatException {

        BookingController bookingController = BookingController.getInstance();

        Booking booking = bookingController.findById(bookingId);
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

        // Check if booking already has ticket with this seat
        for (Ticket ticket: booking.getTickets())
            if (ticket.getStatus() == TicketStatus.VALID && ticket.getSeat().equals(seat))
                return null; // TODO Booking already has ticket with this seat

        // Check if seating is already occupied by other booking
        if (!seating.isAvailable(seat))
            throw new UnavailableBookingSeatException();


        seating.setSeatingStatus(seat, SeatingStatus.TAKEN);
        Ticket ticket = new Ticket(seat, type, new Pricing(cinema.getType(), seat.getType(),
                                                           type, movie.getType()));
        booking.addTicket(ticket);
        entities.put(ticket.getId(), ticket);
        return ticket;
    }

//    public void removeTicket(UUID ticketId) throws IllegalTicketRemovingException {
//
//        Ticket ticket = findById(ticketId);
//        Booking booking = ticket.getBooking();
//        Showtime showtime = booking.getShowtime();
//        ShowtimeSeating seating = showtime.getSeating();
//
//        // Check if booking status not in progress
//        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
//            throw new IllegalTicketRemovingException();
//
//        if (ticket.getStatus() == TicketStatus.VOID)
//            throw new IllegalTicketRemovingException("Ticket has already been removed");
//
//        booking.removeTicket(ticket);
//        seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.AVAILABLE);
//        ticket.setStatus(TicketStatus.VOID);
//    }

//    public void updateTicket(UUID ticketId, Seat newSeat, TicketType newType)
//            throws IllegalTicketUpdatingException, InvalidTicketStatusException,
//            UnavailableTicketTypeException, UnavailableBookingSeatException {
//
//        Ticket ticket = findById(ticketId);
//        Booking booking = ticket.getBooking();
//        List<Ticket> bookingTickets = Arrays.asList(booking.getTickets());
//        Showtime showtime = booking.getShowtime();
//        Cinema cinema = showtime.getCinema();
//        ShowtimeSeating seating = showtime.getSeating();
//        Movie movie = showtime.getMovie();
//
//        // Check if booking status not in progress
//        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
//            throw new IllegalTicketUpdatingException();
//
//        // Check if booking contains ticket
//        if (!bookingTickets.contains(ticket) || ticket.getStatus() == TicketStatus.VOID)
//            throw new InvalidTicketStatusException();
//
//        // Check if ticket type is not available
//        if (!cinema.getType().isAvailable(newType))
//            throw new UnavailableTicketTypeException();
//
//        // Check if seating is already occupied
//        if (!seating.isAvailable(newSeat))
//            throw new UnavailableBookingSeatException();
//
//        Pricing pricing = new Pricing(cinema.getType(), newSeat.getType(),
//                                      newType, movie.getType());
//
//        ticket.setSeat(newSeat);
//        ticket.setType(newType);
//        ticket.setPricing(pricing);
//    }
}
