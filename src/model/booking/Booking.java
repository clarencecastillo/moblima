package model.booking;

import config.BookingConfig;
import model.cinema.Cinema;
import model.commons.Entity;
import model.transaction.Payable;
import model.transaction.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 Represents a booking made by the user to book for tickets for a showtime.
 A booking is payable because it can be made payment for.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class Booking extends Entity implements Payable {

    /**
     * The showtime of this booking.
     */
    private Showtime showtime;

    /**
     * The array list of tickets of this booking.
     */
    private ArrayList<Ticket> tickets;

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
     * @param showtime The showtime of this booking.
     */
    public Booking(Showtime showtime) {
        this.showtime = showtime;
        this.tickets = new ArrayList<Ticket>();
        this.payment = null;
        this.status = BookingStatus.IN_PROGRESS;

    }

    /**
     * Gets the booking status of this booking.
     * @return the booking status of this booking.
     */
    public BookingStatus getStatus() {
        return status;
    }

    /**
     * Gets the showtime of this booking.
     * @return the showtime of this booking.
     */
    public Showtime getShowtime() { return showtime; }

    /**
     * Gets the list of tickets of this booking.
     * @return the list of tickets of this booking.
     */
    public List<Ticket> getTickets() { return tickets; }

    /**
     * Gets the payment of this booking.
     * @return the payment of this booking.
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the payment of this booking.
     * @param payment The payment to be paid for this booking.
     */
    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String getTransactionCode() {
        Cinema cinema = getShowtime().getCinema();
        return cinema.getCode();
    }

    /**
     * Gets the price of this booking, consisting of the price of each ticket and the booking surcharge.
     * @return the price of this booking.
     */
    @Override
    public double getPrice() {
        double price = BookingConfig.getBookingSurcharrge();
        for (Ticket ticket:this.getTickets())
            price += ticket.getPrice();
        return price;
    }

//    @Override
//    public double getPrice() {
//        double price = BookingConfig.getBookingSurcharrge();
//        for (Ticket ticket:this.getTickets())
//            price += getTicketTypePricing(showtime,ticket.getType());
//        return price;
//    }

    /**
     * Changes the showtime of this booking.
     * @param showtime The new showtime of this booking.
     */
    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    /**
     * Changes the status of this booking when the booking is confirmed or cencelled.
     * @param status The new showtime of this booking.
     */
    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    /**
     * Adds a ticket to this booking when the user chooses a ticket type.
     * @param ticket The ticket to be added for this booking.
     */
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}
