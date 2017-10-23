package model.booking;

import config.BookingConfig;
import model.commons.Entity;
import model.transaction.Payable;
import model.transaction.Payment;

import java.util.ArrayList;

public class Booking extends Entity implements Payable {

    private Showtime showtime;
    private ArrayList<Ticket> tickets;
    private Payment payment;
    private BookingStatus status;

    public Booking(Showtime showtime) {
        this.showtime = showtime;
        this.tickets = new ArrayList<Ticket>();
        this.payment = null;
        this.status = BookingStatus.IN_PROGRESS;

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
        this.payment = payment;
    }

    @Override
    public double getPrice() {
        double price = BookingConfig.getBookingSurcharrge();
        for (Ticket ticket:this.getTickets())
            price += ticket.getPrice();
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
}
