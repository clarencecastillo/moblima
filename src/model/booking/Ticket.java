package model.booking;

import java.util.UUID;
import model.cinema.Seat;
import model.commons.Entity;
import model.transaction.Payable;
import model.transaction.Payment;
import model.transaction.Pricing;

public class Ticket extends Entity implements Payable {

    private Seat seat;
    private TicketType type;
    private Pricing pricing;
    private TicketStatus status;
    private Payment payment;

    public Ticket(Seat seat, TicketType type, Pricing pricing) {
        this.seat = seat;
        this.type = type;
        this.pricing = pricing;
        this.status = TicketStatus.VALID;
    }

    public Seat getSeat() {
        return seat;
    }

    public TicketType getType() {
        return type;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    @Override
    public double getPrice() {
        return pricing.getPrice();
    }

    @Override
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public Payment getPayment() {
        return payment;
    }

    @Override
    public boolean isPendingPayment() {
        return false;
    }
}
