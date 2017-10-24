package manager;

import config.BookingConfig;
import exception.*;

import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;
import java.util.stream.Collectors;

import model.booking.*;
import model.cinema.Seat;
import model.cinema.SeatType;
import model.commons.User;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

public class BookingController extends EntityController<Booking> {

    private static BookingController instance;

    private BookingController() {
        super();
    }

    public static void init() {
        instance = new BookingController();
    }

    public static BookingController getInstance() {
        if (instance == null)
            throw new UninitialisedSingletonException();
        return instance;
    }

    // Create a booking when the user chooses the showtime
    public Booking createBooking(UUID showtimeId) throws IllegalShowtimeStatusException {

        ShowtimeController showtimeController = ShowtimeController.getInstance();
        Showtime showtime = showtimeController.findById(showtimeId);

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Create the booking
        Booking booking = new Booking(showtime);

        // Add booking to entities
        entities.put(booking.getId(), booking);

        return booking;
    }


    // Create tickets when user selects ticketTypes
    public void selectTicketType(UUID bookingId, Hashtable<TicketType, Integer> ticketTypesCount)
            throws ExceedBookingSeatException, UnavailableTicketTypeException, IllegalBookingStatusException {

        Booking booking = findById(bookingId);

        // Check if booking not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            throw new IllegalBookingStatusException("The booking cannot be modified");

        // Check if all ticket types are available for this booking cinema
        for (TicketType ticketType : ticketTypesCount.keySet())
            if (!booking.getShowtime().getCinema().getType().isAvailable(ticketType))
                throw new UnavailableTicketTypeException();

        // Check if number of ticket types exceed maximum seats per booking
        int totalCount = ticketTypesCount.values().stream().mapToInt(Integer::intValue).sum();
        if (totalCount > BookingConfig.getMaxSeatsPerBooking())
            throw new ExceedBookingSeatException();

        for (TicketType ticketType : ticketTypesCount.keySet())
            for (int i = 0; i < ticketTypesCount.get(ticketType); i++)
                booking.addTicket(new Ticket(ticketType));
    }

    // Set seats to tickets according to the order when user selects seats
    public void selectSeat(UUID bookingId, Seat[] seats) throws UnavailableBookingSeatException,
            InsufficientSeatsException, IllegalBookingStatusException {

        Booking booking = findById(bookingId);

        // Check if booking not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            throw new IllegalBookingStatusException("The booking cannot be modified");

        // Check if the number of seats the same with the tickets
        if (seats.length != booking.getTickets().length)
            throw new InsufficientSeatsException();

        // Check if all seats are available
        for (Seat seat : seats)
            if (!booking.getShowtime().getSeating().isAvailable(seat))
                throw new UnavailableBookingSeatException();

        // Arbitrarily pair seats to the ticket types
        for (int i = 0; i < seats.length; i++)
            booking.getTickets()[i].setSeat(seats[i]);
    }

    public double getBookingPrice(UUID bookingId) {
        Booking booking = findById(bookingId);
        double price = BookingConfig.getBookingSurcharrge();
        for (Ticket ticket:booking.getTickets())
            price += ticket.getPrice();
        return price;
    }

    // Assign booking to the user after booking is confirmed
    public void confirmBooking(UUID bookingId, UUID userId)
            throws IllegalShowtimeStatusException, UnpaidPaymentException, IllegalShowtimeBookingException,
            ExceedBookingSeatException, UnavailableTicketTypeException, UnavailableBookingSeatException,
            IllegalBookingStatusException {

        UserController userController = UserController.getInstance();

        Booking booking = findById(bookingId);
        Showtime showtime = booking.getShowtime();

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Check if booking not in progress
        BookingStatus previousStatus = booking.getStatus();
        if (previousStatus != BookingStatus.IN_PROGRESS)
            throw new IllegalBookingStatusException("The booking can not be confirmed");

        // Check whether payment is made
        Payment payment = booking.getPayment();
        if (payment.getStatus() != PaymentStatus.ACCEPTED)
            throw new UnpaidPaymentException();

        booking.setStatus(BookingStatus.CONFIRMED);
        showtime.addBooking(booking);

        User user = userController.findById(userId);
        user.addBooking(findById(bookingId));

        // Mark all seats for the booking as taken
        ShowtimeSeating seating = showtime.getSeating();
        for(Ticket ticket: booking.getTickets())
            seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.TAKEN);
    }

//    public void changeBookingStatus(UUID bookingId, BookingStatus status) throws IllegalShowtimeStatusException,
//        IllegalBookingStatusException, UnpaidPaymentException, UnpaidBookingChargeException {
//
//        Booking booking = findById(bookingId);
//        Showtime showtime = booking.getShowtime();
//
//        // Check if showtime is still open for booking
//        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
//            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");
//
//
//        ShowtimeSeating seating = showtime.getSeating();
//        Payment payment = booking.getPayment();
//        Ticket[] tickets = booking.getTickets();
//        BookingStatus previousStatus = booking.getStatus();
//        switch (status) {
//            case CONFIRMED:
//
//
//                // Mark all seats for the booking as taken
//                for(Ticket ticket: tickets)
//                    seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.TAKEN);
//
//                break;
//
//            case CANCELLED:
//
////                // Check if already paid and if refunds are allowed
////                if (previousStatus == BookingStatus.CONFIRMED &&
////                    payment != null && payment.getStatus() == PaymentStatus.ACCEPTED &&
////                    PaymentConfig.isRefundsAllowed()) {
////
////                    // Refund booking payment and other refundable charges
////                    payment.setStatus(PaymentStatus.REFUNDED);
////                    for (BookingCharge charge: charges)
////                        if (charge.isRefundable())
////                            charge.getPayment().setStatus(PaymentStatus.REFUNDED);
////                }
//
//                // Mark all seats for the booking as available
//                for (Ticket ticket: tickets)
//                    seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.AVAILABLE);
//
//                break;
//        }
//
//        booking.setStatus(status);
//    }

    // Cancel booking
    public void cancelBooking(UUID bookingId) {
        Booking booking = findById(bookingId);
        BookingStatus previousStatus = booking.getStatus();
//        if previousStatus == BookingStatus.CONFIRMED
        booking.setStatus(BookingStatus.CANCELLED);
    }

}
