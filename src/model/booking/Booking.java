package model.booking;

import config.BookingConfig;
import model.cineplex.Cinema;
import model.cineplex.Seat;
import model.commons.Entity;
import model.transaction.Payable;
import model.transaction.Payment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Represents a booking made by the user to book for tickets for a showtime.
 * A booking is payable because it can be made payment for.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Booking extends Entity implements Payable {

    /**
     * The showtime of this booking.
     */
    private Showtime showtime;

    /**
     * A hash table with ticket types as the key and the quantity of this ticket type as the key of this booking.
     */
    private Hashtable<TicketType, Integer> ticketTypesCount;

    /**
     * An array list of seats of this booking.
     */
    private ArrayList<Seat> seats;

    /**
     * The payment of this booking.
     */
    private Payment payment;

    /**
     * The booking status of this booking.
     */
    private BookingStatus status;

    /**
     * Creates a booking with the given showtime.
     * A booking contains an empty array list of tickets when initially being created.
     * A booking has no payment when initially being created.
     * The status of booking is set to be in progress when the booking is initially created.
     *
     * @param showtime The showtime of this booking.
     */
    public Booking(Showtime showtime) {
        this.showtime = showtime;
        this.ticketTypesCount = new Hashtable<>();
        this.seats = new ArrayList<>();
        this.payment = null;
        this.status = BookingStatus.IN_PROGRESS;
    }

    /**
     * Gets the booking status of this booking.
     *
     * @return the booking status of this booking.
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * Changes the status of this booking when the booking is confirmed or cencelled.
     *
     * @param status The new showtime of this booking.
     */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /**
     * Gets the showtime of this booking.
     *
     * @return the showtime of this booking.
     */
    public Showtime getShowtime() {
        return showtime;
    }

    /**
     * Changes the showtime of this booking.
     *
     * @param showtime The new showtime of this booking.
     */
    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    /**
     * Gets a hash table with ticket types as the key and the quantity of this ticket type as the key of this booking.
     * @return a hash table with ticket types as the key and the quantity of this ticket type
     * as the key of this booking.
     */
    public Hashtable<TicketType, Integer> getTicketTypesCount() {
        return ticketTypesCount;
    }

    /**
     * Gets an array list of seats of this booking.
     * @return an array list of seats of this booking.
     */
    public List<Seat> getSeats() {
        return seats;
    }

    public int getTotalTicketsCount() {
        return ticketTypesCount.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Gets the payment of this booking.
     *
     * @return the payment of this booking.
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the payment of this booking.
     *
     * @param payment The payment to be paid for this booking.
     */
    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

//    @Override
//    public double getPrice() {
//        double price = BookingConfig.getBookingSurcharrge();
//        for (Ticket ticket:this.getTickets())
//            price += getTicketTypePricing(showtime,ticket.getType());
//        return price;
//    }

    @Override
    public String getTransactionCode() {
        Cinema cinema = getShowtime().getCinema();
        return cinema.getCode();
    }

    /**
     * Gets the price of this booking, consisting of the price of each ticket and the booking surcharge.
     *
     * @return the price of this booking.
     */
    @Override
    public double getPrice() {
        int totalTickets = ticketTypesCount.values().stream().mapToInt(Integer::intValue).sum();
        double price = BookingConfig.getBookingSurcharrge();
        for (TicketType ticketType : ticketTypesCount.keySet())
            price += ticketType.getPrice() * ticketTypesCount.get(ticketType);
        price += totalTickets * showtime.getMovie().getType().getPrice();
        price += totalTickets * showtime.getCinema().getType().getPrice();
        return price;
    }

    /**
     * Changes the ticket types selected by this booking.
     * @param ticketTypesCount the new ticket types selected by this booking.
     */
    public void setTicketTypesCount(Hashtable<TicketType, Integer> ticketTypesCount) {
        this.ticketTypesCount = ticketTypesCount;
    }

    /**
     * Changes the seats selected by this booking.
     * @param seats the new seats selected by this booking.
     */
    public void setSeats(List<Seat> seats) {
        this.seats.clear();
        this.seats.addAll(seats);
    }
}
