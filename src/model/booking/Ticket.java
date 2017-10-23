package model.booking;

import model.cinema.Seat;
import model.commons.Entity;
import model.transaction.Priceable;
import model.transaction.Pricing;

public class Ticket implements Priceable {

    private Seat seat;
    private TicketType type;

    public Ticket(TicketType type) {
        this.seat = null;
        this.type = type;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) { this.seat = seat; }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return type.getPrice();
    }
}
