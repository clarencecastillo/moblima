package model.booking;

import java.util.UUID;
import model.cinema.Seat;
import model.commons.Entity;
import model.transaction.Pricing;

public class Ticket extends Entity {

    private Seat seat;
    private TicketType type;
    private Pricing pricing;
    private Booking booking;
    private TicketStatus status;

    public Ticket(Seat seat, TicketType type, Pricing pricing, Booking booking) {
        super(UUID.randomUUID());
        this.seat = seat;
        this.type = type;
        this.pricing = pricing;
        this.booking = booking;
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

    public Booking getBooking() {
        return booking;
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

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
