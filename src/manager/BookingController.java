package manager;

import exception.*;

import java.util.UUID;

import model.booking.*;
import model.cinema.Seat;
import model.cinema.SeatType;
import model.commons.User;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

public class BookingController extends EntityController<Booking> {

    // Eager Singleton
    private static BookingController instance = new BookingController();

    private BookingController() {
        super();
    }

    public static BookingController getInstance() {
        return instance;
    }

    public Booking createBooking(UUID showtimeId) throws IllegalShowtimeStatusException {

        ShowtimeController showtimeController = ShowtimeController.getInstance();

        Showtime showtime = showtimeController.findById(showtimeId);

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Create the booking
        Booking booking = new Booking(showtime);

        return booking;
    }

    public void selectTicketType(UUID bookingId, TicketType ticketType) {
        BookingController bookingController = BookingController.getInstance();
        Booking booking = bookingController.findById(bookingId);
        booking.addTicketType(ticketType);
    }

    public void selectSeat(UUID bookingId, Seat seat) {
        BookingController bookingController = BookingController.getInstance();
        Booking booking = bookingController.findById(bookingId);
        booking.addSeat(seat);
    }

    public void confirmBooking(UUID bookingId, UUID userId, Seat[] seats, TicketType[] types)
            throws IllegalShowtimeStatusException, UnpaidPaymentException, IllegalShowtimeBookingException,
            ExceedBookingSeatException, UnavailableTicketTypeException, UnavailableBookingSeatException {

        UserController userManager = UserController.getInstance();
        BookingController bookingController = BookingController.getInstance();
        ShowtimeController showtimeManager = ShowtimeController.getInstance();
        Booking booking = bookingController.findById(bookingId);
        Showtime showtime = booking.getShowtime();

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Check whether payment is made
        Payment payment = booking.getPayment();
        if (payment.getStatus() != PaymentStatus.ACCEPTED)
            throw new UnpaidPaymentException();

        booking.setStatus(BookingStatus.CONFIRMED);

        User user = userManager.findById(userId);
        user.addBooking(bookingController.findById(bookingId));

        TicketController ticketController = TicketController.getInstance();
        for (int i = 0; i < seats.length; i++) {
            ticketController.createTicket(booking,seats[i], types[i]);
        }

        // Mark all seats for the booking as taken
        ShowtimeSeating seating = showtime.getSeating();
        for(Seat seat: seats)
            seating.setSeatingStatus(seat, SeatingStatus.TAKEN);

        // Add booking to entities
        showtime.addBooking(booking);
        entities.put(booking.getId(), booking);
    }

    public void changeBookingStatus(UUID bookingId, BookingStatus status) throws IllegalShowtimeStatusException,
        IllegalBookingStatusException, UnpaidPaymentException, UnpaidBookingChargeException {

        Booking booking = findById(bookingId);
        Showtime showtime = booking.getShowtime();

        // Check if showtime is still open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");


        ShowtimeSeating seating = showtime.getSeating();
        Payment payment = booking.getPayment();
        Ticket[] tickets = booking.getTickets();
        BookingStatus previousStatus = booking.getStatus();
        switch (status) {
            case CONFIRMED:

                // Check if booking not in progress
                if (previousStatus != BookingStatus.IN_PROGRESS)
                    throw new IllegalBookingStatusException("The booking can not be confirmed");

                // Check if booking not yet paid for
                if (payment.getStatus() != PaymentStatus.ACCEPTED)
                    throw new UnpaidPaymentException();

                // Mark all seats for the booking as taken
                for(Ticket ticket: tickets)
                    seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.TAKEN);

                break;

            case CANCELLED:

//                // Check if already paid and if refunds are allowed
//                if (previousStatus == BookingStatus.CONFIRMED &&
//                    payment != null && payment.getStatus() == PaymentStatus.ACCEPTED &&
//                    PaymentConfig.isRefundsAllowed()) {
//
//                    // Refund booking payment and other refundable charges
//                    payment.setStatus(PaymentStatus.REFUNDED);
//                    for (BookingCharge charge: charges)
//                        if (charge.isRefundable())
//                            charge.getPayment().setStatus(PaymentStatus.REFUNDED);
//                }

                // Mark all seats for the booking as available
                for (Ticket ticket: tickets)
                    seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.AVAILABLE);

                break;
        }

        booking.setStatus(status);
    }

}
