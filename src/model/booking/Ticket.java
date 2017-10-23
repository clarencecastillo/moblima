package model.booking;

import model.cinema.Seat;
import model.commons.Entity;
import model.transaction.Priceable;
import model.transaction.Pricing;

public class Ticket extends Entity implements Priceable {

    private Seat seat;
    private TicketType type;
    private Pricing pricing;

    public Ticket(Seat seat, TicketType type, Pricing pricing) {
        this.seat = seat;
        this.type = type;
        this.pricing = pricing;
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

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public double getPrice() {
        return pricing.getPrice();
    }
}
