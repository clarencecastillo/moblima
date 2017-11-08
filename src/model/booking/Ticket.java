package model.booking;

import model.cineplex.Seat;
import model.transaction.Priceable;

/**
 * Represents a ticket that belongs to a booking.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Ticket implements Priceable {
    /**
     * The seat of this ticket.
     */
    private Seat seat;

    /**
     * The type of this ticket.
     */
    private TicketType type;

    private Booking booking;

    /**
     * Creates a ticket with the given ticket type when the use selects the ticket type during booking process.
     *
     * @param type the selected ticket type of this ticket.
     * @param booking the booking that this ticket belongs to.
     */
    public Ticket(TicketType type, Booking booking) {
        this.seat = null;
        this.type = type;
        this.booking = booking;
    }

    /**
     * Gets the seat of this ticket.
     *
     * @return the seat of this ticket.
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Sets the seat of this ticket.
     *
     * @param seat the seat to be set for this ticket.
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /**
     * Gets the type of this ticket.
     *
     * @return the type of this ticket.
     */
    public TicketType getType() {
        return type;
    }

    /**
     * Change the type of this ticket.
     *
     * @param type the new type of this ticket.
     */
    public void setType(TicketType type) {
        this.type = type;
    }

    /**
     * Gets the booking of this ticket.
     *
     * @return the booking of this ticket.
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Change the type of this ticket.
     *
     * @param booking the new type of this ticket.
     */
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    /**
     * Gets the price of this ticket which is calculated by the total pricing rates of the ticket type,
     * the booking's showtime's cineplex type and movie type.
     *
     * @return the price of this ticket.
     */
    @Override
    public double getPrice() {
        return type.getPrice() +
                booking.getShowtime().getCinema().getType().getPrice() +
                booking.getShowtime().getMovie().getType().getPrice();
    }
}
