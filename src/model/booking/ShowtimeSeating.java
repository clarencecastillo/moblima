package model.booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import exception.SeatNotFoundException;
import model.cinema.Seat;
import model.cinema.SeatType;

/**
 Represents a seating of a showtime, consisting the all seats and their status.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class ShowtimeSeating implements Serializable {

    /**
     * A hashtable with seat as the key and its status as the value.
     */
    private Hashtable<Seat, SeatingStatus> seatings;

    /**
     * Creates a showtime seating for the given showtime.
     * The seating will be all the seats in the cinema layout of the assigned cinema of the showtime.
     * All seats are set to be available when initially created.
     * @param showtime the showtime seating of which to be creates of.
     */
    public ShowtimeSeating(Showtime showtime) {
        seatings = new Hashtable<>();
        for (Seat seat: showtime.getCinema().getLayout().getSeats())
            seatings.put(seat, SeatingStatus.AVAILABLE);
    }

    /**
     * * Gets the status of a seat in this showtime seating.
     * @param seat The seat of whose availability is to to be checked.
     * @return the status of a seat in this showtime seating if the seat is in this seat is in the showtime seating.
     * @throws SeatNotFoundException if the seat is not in this showtime seating.
     */
    public SeatingStatus getSeatingStatus(Seat seat) throws SeatNotFoundException {
        SeatingStatus seatingStatus = seatings.get(seat);
        if (seatingStatus == null)
            throw new SeatNotFoundException();
        return seatingStatus;
    }

    /**
     * Checks whether the seat is available in this showtime seating.
     * @param seat The seat to be checked.
     * @return true is the status of the seat is available.
     * @throws SeatNotFoundException if the seat is not in this showtime seating.
     */
    public boolean isAvailable(Seat seat) throws SeatNotFoundException {
        return getSeatingStatus(seat) == SeatingStatus.AVAILABLE;
    }

    /**
     * Change the status of the seat in this seating.
     * @param seat The seat of whose status of be changed.
     * @param seatingStatus The new status of seat to be changes in this showtime seating.
     * @exception if the seat is not in this showtime seating.
     */
    public void setSeatingStatus(Seat seat, SeatingStatus seatingStatus) throws SeatNotFoundException {
        if (!hasSeat(seat))
            throw new SeatNotFoundException();
        seatings.put(seat, seatingStatus);
    }

    /**
     * Checks whether a given seat can be found in this showtime seating.
     * @param seat The seat to be checked.
     * @return true if this showtime seating contains the seat.
     */
    public boolean hasSeat(Seat seat) {
        return seatings.containsKey(seat);
    }
//    /**
//     * Gets the list of seats in the given status in this showtime seating.
//     * @param status The status of the seats to be returned.
//     * @return the list of seats in the given status.
//     * @throws SeatNotFoundException
//     */
//    public List<Seat> getSeats(SeatingStatus status) throws SeatNotFoundException {
//        ArrayList<Seat> seats = new ArrayList<Seat>();
//        for (SeatType seatType: SeatType.values()) {
//            seats.addAll(getAvailableSeats(seatType));
//        }
//        return seats;
//    }
//
//    /**
//     * Gets a list of seats of a given seat type that is available in this showtime seating.
//     * @param seatType The seat type of the seats to be returned.
//     * @return a list of seats of a given seat type that is available in this showtime seating.
//     * @throws SeatNotFoundException
//     */
//    public ArrayList<Seat> getAvailableSeats(SeatType seatType) throws SeatNotFoundException {
//        ArrayList<Seat> availableSeats = new ArrayList<Seat>();
//        for(Seat seat : seatings.keySet()) {
//            if (seat.getType() == seatType && isAvailable(seat))
//                availableSeats.add(seat);
//        }
//        return availableSeats;
//    }

//    public ArrayList<Seat> getSeats() {
//        return new ArrayList<Seat>(seatings.keySet());
//    }
}
