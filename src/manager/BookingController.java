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

    // Create a booking when user chooses the showtime
    public Booking createBooking(UUID showtimeId) throws IllegalShowtimeStatusException {

        ShowtimeController showtimeController = ShowtimeController.getInstance();

        Showtime showtime = showtimeController.findById(showtimeId);

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Create the booking
        Booking booking = new Booking(showtime);

        // Add booking to entities
        showtime.addBooking(booking);
        entities.put(booking.getId(), booking);

        return booking;
    }

    // Add ticketTypes and seats into the booking when user select them
    public void selectTicketType(UUID bookingId, TicketType ticketType) {
        Booking booking = findById(bookingId);
        booking.addTicketType(ticketType);
    }

    public void selectSeat(UUID bookingId, Seat seat) {
        Booking booking = findById(bookingId);
        booking.addSeat(seat);
    }

    // Assign booking to the user after capturing user information
    public void assignBooking(UUID bookingId, UUID userId)
            throws IllegalShowtimeStatusException, UnpaidPaymentException, IllegalShowtimeBookingException,
            ExceedBookingSeatException, UnavailableTicketTypeException, UnavailableBookingSeatException {

        UserController userManager = UserController.getInstance();
        ShowtimeController showtimeManager = ShowtimeController.getInstance();
        Booking booking = findById(bookingId);
        Showtime showtime = booking.getShowtime();



        booking.setStatus(BookingStatus.CONFIRMED);

        User user = userManager.findById(userId);
        user.addBooking(findById(bookingId));
    }

    // Confirm booking and create tickets after payment is made
    public void confirmBooking(UUID bookingId){

        ShowtimeController showtimeManager = ShowtimeController.getInstance();
        Booking booking = findById(bookingId);
        Showtime showtime = booking.getShowtime();

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Check whether payment is made
        Payment payment = booking.getPayment();
        if (payment.getStatus() != PaymentStatus.ACCEPTED)
            throw new UnpaidPaymentException();

        // Create tickets
        TicketController ticketController = TicketController.getInstance();
        Seat[] seats = booking.getSeat();
        TicketType[] ticketTypes = booking.getTicketType();
        for (int i = 0; i < seats.length; i++) {
            ticketController.createTicket(booking,seats[i], ticketTypes[i]);
        }

        // Mark all seats for the booking as taken
        ShowtimeSeating seating = showtime.getSeating();
        for(Seat seat: seats)
            seating.setSeatingStatus(seat, SeatingStatus.TAKEN);
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

    // Cancel booking
    public void cancelBooking(UUID bookingId, UUID)

}
