package model.booking;

import java.util.ArrayList;

import config.BookingConfig;
import model.cinema.Seat;
import model.commons.Entity;
import model.commons.User;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;
import model.transaction.Pricing;

public class Booking extends Entity implements Payable {

    private Showtime showtime;
    private ArrayList<Ticket> tickets;
    private Payment payment;
    private BookingStatus status;
    private ArrayList<TicketType> ticketTypes;
    private ArrayList<Seat> seats;

    public Booking(Showtime showtime) {
        this.showtime = showtime;
        this.tickets = new ArrayList<Ticket>();
        this.payment = null;
        this.status = BookingStatus.IN_PROGRESS;
        this.ticketTypes = new ArrayList<TicketType>();
        this.seats = new ArrayList<Seat>();

    }

    public BookingStatus getStatus() {
        return status;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Ticket[] getTickets() {
        return tickets.toArray(new Ticket[tickets.size()]);
    }

    public Payment getPayment() {
        return payment;
    }

    @Override
    public void setPayment(Payment payment) {
        this.payment = new Payment(this.getPrice());
    }

    @Override
    public double getPrice() {
        double price = 0;
        for(Ticket ticket: tickets)
            price += ticket.getPricing().getPrice();
        // Add the booking processing fee to bookingPayment
        price += BookingConfig.getBookingSurcharrge();
        return price;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void addTicketType(TicketType ticketType) {ticketTypes.add(ticketType); }

    public void addSeat(Seat seat) {seats.add(seat); }

    public TicketType[] getTicketType() { return ticketTypes.toArray(new TicketType[ticketTypes.size()]); }

    public Seat[] getSeat() { return seats.toArray(new Seat[seats.size()]); }
}
