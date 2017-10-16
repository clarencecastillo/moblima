package manager;

import config.BookingConfig;
import config.PaymentConfig;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import manager.exception.*;
import model.booking.Booking;
import model.booking.BookingCharge;
import model.booking.BookingStatus;
import model.booking.SeatingStatus;
import model.booking.Showtime;
import model.booking.ShowtimeSeating;
import model.booking.ShowtimeStatus;
import model.booking.Ticket;
import model.commons.User;
import model.transaction.Payment;
import model.transaction.PaymentStatus;
import util.Utilities;

public class BookingManager extends EntityManager<Booking> {

    private static BookingManager instance;

    private BookingManager() {
        super();
    }

    public static BookingManager getInstance() {
        if (instance == null)
            instance = new BookingManager();
        return instance;
    }

    public Booking createBooking(UUID userId, UUID showtimeId) throws IllegalShowtimeStatusException {

        UserManager userManager = UserManager.getInstance();
        ShowtimeManager showtimeManager = ShowtimeManager.getInstance();

        User user = userManager.findById(userId);
        Showtime showtime = showtimeManager.findById(showtimeId);

        // Check whether showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Create the booking
        Date now = new Date();
        Booking booking = new Booking(showtime, now, user);

        // Add booking fee if any
        double bookingSurcharge = BookingConfig.getBookingSurcharrge();
        if (bookingSurcharge > 0)
            booking.addCharge(new BookingCharge(bookingSurcharge, "Booking Fee",
                                                false));

        // Add booking to entities
        user.addBooking(booking);
        showtime.addBooking(booking);
        entities.put(booking.getId(), booking);
        return booking;
    }

    public void changeBookingStatus(UUID bookingId, BookingStatus status) throws IllegalShowtimeStatusException,
            IllegalBookingStatusException,UnpaidPaymentException,UnpaidBookingChargeException,IllegalBookingChangeException {

        Booking booking = findById(bookingId);
        Showtime showtime = booking.getShowtime();

        // Check if showtime is still open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalShowtimeStatusException("Can only book when the movie is open for booking");

        // Check if state is already set
        if (booking.getStatus() == status)
            throw new IllegalBookingStatusException("The booking is already "+ status);

        ShowtimeSeating seating = showtime.getSeating();
        Payment payment = booking.getPayment();
        BookingCharge[] charges = booking.getCharges();
        Ticket[] tickets = booking.getTickets();
        BookingStatus previousStatus = booking.getStatus();
        switch (status) {
            case CONFIRMED:

                // Check if booking not in progress
                if (previousStatus != BookingStatus.IN_PROGRESS)
                    throw new IllegalBookingStatusException("The booking can not be confirmed");

                // Check if booking not yet paid for
                if (payment == null || payment.getStatus() != PaymentStatus.ACCEPTED)
                    throw new UnpaidPaymentException();

                // Check for other charges not yet paid for
                for (BookingCharge charge: charges)
                    if (charge.isPendingPayment())
                        throw new UnpaidBookingChargeException();

                // Mark all seats for the booking as taken
                for(Ticket ticket: tickets)
                    seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.TAKEN);

                break;

            case CANCELLED:

                // Check if already paid and if refunds are allowed
                if (previousStatus == BookingStatus.CONFIRMED &&
                    payment != null && payment.getStatus() == PaymentStatus.ACCEPTED &&
                    PaymentConfig.isRefundsAllowed()) {

                    // Refund booking payment and other refundable charges
                    payment.setStatus(PaymentStatus.REFUNDED);
                    for (BookingCharge charge: charges)
                        if (charge.isRefundable())
                            charge.getPayment().setStatus(PaymentStatus.REFUNDED);
                }

                // Mark all seats for the booking as available
                for (Ticket ticket: tickets)
                    seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.AVAILABLE);

                break;

            case IN_PROGRESS:

                // Happens when user wants to change booking showtime or seating

                // Check if still within grace period to change booking
                int bookingChangesGraceMinutes = BookingConfig.getBookingChangesGraceMinutes();
                if (Utilities.getDateBefore(showtime.getStartTime(), Calendar.MINUTE,
                                            bookingChangesGraceMinutes).after(new Date()))
                    throw new IllegalBookingChangeException();

                // Check if booking already cancelled
                if (previousStatus == BookingStatus.CANCELLED)
                    throw new IllegalBookingChangeException("Booking cannot be changed as it is already cancelled");

                // Add booking changes fee if any
                double bookingChangesSurcharge = BookingConfig.getBookingChangesSurcharge();
                if (bookingChangesSurcharge > 0)
                    booking.addCharge(new BookingCharge(bookingChangesSurcharge,
                                                        "Booking Changes Fee",
                                                        false));
        }

        booking.setStatus(status);
    }

    public void changeBookingShowtime(UUID bookingId, UUID showtimeId) throws IllegalBookingChangeException{

        ShowtimeManager showtimeManager = ShowtimeManager.getInstance();

        Booking booking = findById(bookingId);
        Showtime showtime = showtimeManager.findById(showtimeId);

        // Check if booking status not in progress
        if (booking.getStatus() != BookingStatus.IN_PROGRESS)
            throw new IllegalBookingChangeException("Can only change showtime for bookings in progress");

        // Check if showtime is the previously set showtime
        Showtime previousShowtime = booking.getShowtime();
        if (previousShowtime.equals(showtime))
            throw new IllegalBookingChangeException("Can only change to another showtime");

        // Check if showtime is open for booking
        if (showtime.getStatus() != ShowtimeStatus.OPEN_BOOKING)
            throw new IllegalBookingChangeException("This showtime is not open for booking yet.");

        // Remove this booking from previous showtime
        previousShowtime.removeBooking(booking);

        // Free previously taken seats from old showtime
        ShowtimeSeating seating = showtime.getSeating();
        Iterator<Ticket> ticketIterator = Arrays.asList(booking.getTickets()).iterator();
        while (ticketIterator.hasNext()) {
            Ticket ticket = ticketIterator.next();
            if (seating.getSeatingStatus(ticket.getSeat()) == SeatingStatus.TAKEN)
                seating.setSeatingStatus(ticket.getSeat(), SeatingStatus.AVAILABLE);
            ticketIterator.remove();
        }

        booking.setShowtime(showtime);
    }
}
