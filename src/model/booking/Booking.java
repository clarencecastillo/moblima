package model.booking;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import model.commons.Entity;
import model.commons.User;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.PaymentStatus;

public class Booking extends Entity implements Payable {

    private Showtime showtime;
    private ArrayList<Ticket> tickets;
    private BookingPayment payment;
    private ArrayList<BookingCharge> charges;
    private BookingStatus status;

    public Booking(Showtime showtime) {
        this.showtime = showtime;
        this.tickets = new ArrayList<Ticket>();
        this.payment = null;
        this.charges = new ArrayList<BookingCharge>();
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

    public BookingCharge[] getCharges() {
        return charges.toArray(new BookingCharge[charges.size()]);
    }

    @Override
    public boolean isPendingPayment() {
        return getStatus() == BookingStatus.IN_PROGRESS &&
               (payment == null || payment.getStatus() != PaymentStatus.ACCEPTED);
    }

    @Override
    public double getPrice() {
        double price = 0;
        for(Ticket ticket: tickets) {
            price += ticket.getPricing().getPrice();
        }
        return price;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public void setPayment(Payment payment) {
        this.payment = new BookingPayment(payment);
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public void addCharge(BookingCharge charge) {
        charges.add(charge);
    }

    public void removeCharge(BookingCharge charge) {
        charges.remove(charge);
    }
}
