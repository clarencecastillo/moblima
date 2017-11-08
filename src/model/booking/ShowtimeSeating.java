package model.booking;

import exception.IllegalActionException;
import model.cineplex.Seat;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Represents a seating of a showtime, consisting the all seats and their status.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class ShowtimeSeating implements Serializable {

    /**
     * A hashtable with seat as the key and its status as the value.
     */
    private Hashtable<Seat, SeatingStatus> seatings;

    /**
     * Creates a showtime seating for the given showtime.
     * The seating will be all the seats in the cineplex layout of the assigned cineplex of the showtime.
     * All seats are set to be available when initially created.
     *
     * @param showtime the showtime seating of which to be creates of.
     */
    public ShowtimeSeating(Showtime showtime) {
        seatings = new Hashtable<>();
        for (Seat seat : showtime.getCinema().getLayout().getSeats())
            seatings.put(seat, SeatingStatus.AVAILABLE);
    }

    /**
     * * Gets the status of a seat in this showtime seating.
     *
     * @param seat The seat of whose availability is to to be checked.
     * @return the status of a seat in this showtime seating if the seat is in this seat is in the showtime seating.
     * @throws IllegalActionException if the seat is not in this showtime seating.
     */
    public SeatingStatus getSeatingStatus(Seat seat) throws IllegalActionException {
        SeatingStatus seatingStatus = seatings.get(seat);
        if (seatingStatus == null)
            throw new IllegalActionException("This seat is not found in this showtime's seating");
        return seatingStatus;
    }

    /**
     * Checks whether the seat is available in this showtime seating.
     *
     * @param seat The seat to be checked.
     * @return true is the status of the seat is available.
     * @throws IllegalActionException if the seat is not in this showtime seating.
     */
    public boolean isAvailable(Seat seat) throws IllegalActionException {
        return getSeatingStatus(seat) == SeatingStatus.AVAILABLE;
    }

    /**
     * Gets the seat by the given row and column.
     * @param row The row of this seat to be returned.
     * @param column The column of this seat to be returned.
     * @return the seat by the given row and column.
     */
    public Seat getSeatAt(char row, int column) throws IllegalActionException {
        for (Seat seat : seatings.keySet())
            if (seat.getRow() == row && seat.getColumn() == column)
                return seat;
        return null;
    }

    /**
     * Change the status of the seat in this seating.
     *
     * @param seat          The seat of whose status of be changed.
     * @param seatingStatus The new status of seat to be changes in this showtime seating.
     * @throws IllegalActionException if the seat is not in this showtime seating.
     */
    public void setSeatingStatus(Seat seat, SeatingStatus seatingStatus) throws IllegalActionException {
        if (!hasSeat(seat))
            throw new IllegalActionException("This seat is not found in this showtime's seating");
        seatings.put(seat, seatingStatus);
    }

    /**
     * Checks whether a given seat can be found in this showtime seating.
     *
     * @param seat The seat to be checked.
     * @return true if this showtime seating contains the seat.
     */
    public boolean hasSeat(Seat seat) {
        return seatings.containsKey(seat);
    }
}
