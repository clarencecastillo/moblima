package manager;

import config.BookingConfig;
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

public class TicketManager extends EntityManager<Ticket> {

    private static TicketManager instance = new TicketManager();

    private TicketManager() {
        super();
    }

    public static TicketManager getInstance() {
        return instance;
    }

    public Ticket createTicket(UUID bookingId, Seat seat, TicketType type) {

        BookingManager bookingManager = BookingManager.getInstance();

        Booking booking = bookingManager.findById(bookingId);
        Showtime showtime = booking.getShowtime();
        Cinema cinema = showtime.getCinema();
        ShowtimeSeating seating = showtime.getSeating();
        Movie movie = showtime.getMovie();

        // Check if booking status not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            return null; // TODO Can only change showtime for bookings in progress

        int maxSeatsPerBooking = BookingConfig.getMaxSeatsPerBooking();
        if (booking.getTickets().length == maxSeatsPerBooking)
            return null; // TODO Already reached maximum number of seats for booking

        // Check if ticket type is not available
        if (!cinema.getType().isAvailable(type))
            return null; // TODO Ticket type not available

        for (Ticket ticket: booking.getTickets())
            if (ticket.getStatus() == TicketStatus.VALID && ticket.getSeat().equals(seat))
                return null; // TODO Booking already has ticket with this seat

        // Check if seating is already occupied
        if (!seating.isAvailable(seat))
            return null; // TODO Ticket already occupied

        seating.setSeatingStatus(seat, SeatingStatus.TAKEN);
        Ticket ticket = new Ticket(seat, type, new Pricing(cinema.getType(), seat.getType(),
                                                           type, movie.getType()), booking);
        booking.addTicket(ticket);
        entities.put(ticket.getId(), ticket);
        return ticket;
    }

    public void removeTicket(UUID ticketId) {

        Ticket ticket = findById(ticketId);
        Booking booking = ticket.getBooking();
        Showtime showtime = booking.getShowtime();
        ShowtimeSeating seating = showtime.getSeating();

        // Check if booking status not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            return; // TODO Can only change showtime for bookings in progress

        if (ticket.getStatus() == TicketStatus.VOID)
            return; // TODO ticket is already removed

        booking.removeTicket(ticket);
        seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.AVAILABLE);
        ticket.setStatus(TicketStatus.VOID);
    }

    public void updateTicket(UUID ticketId, Seat newSeat, TicketType newType) {

        Ticket ticket = findById(ticketId);
        Booking booking = ticket.getBooking();
        List<Ticket> bookingTickets = Arrays.asList(booking.getTickets());
        Showtime showtime = booking.getShowtime();
        Cinema cinema = showtime.getCinema();
        ShowtimeSeating seating = showtime.getSeating();
        Movie movie = showtime.getMovie();

        // Check if booking status not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            return; // TODO Can only change showtime for bookings in progress

        // Check if booking contains ticket
        if (!bookingTickets.contains(ticket) || ticket.getStatus() == TicketStatus.VOID)
            return; // TODO Invalid ticket ID

        // Check if ticket type is not available
        if (!cinema.getType().isAvailable(newType))
            return; // TODO Ticket type not available

        // Check if seat is already occupied
        if (!seating.isAvailable(newSeat) && !ticket.getSeat().equals(newSeat))
            return; // TODO Ticket already occupied

        Pricing pricing = new Pricing(cinema.getType(), newSeat.getType(),
                                      newType, movie.getType());

        ticket.setSeat(newSeat);
        ticket.setType(newType);
        ticket.setPricing(pricing);
    }
}
