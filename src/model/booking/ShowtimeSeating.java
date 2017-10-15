package model.booking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import model.cinema.Seat;
import model.cinema.SeatType;

public class ShowtimeSeating implements Serializable {

    private Hashtable<Seat, SeatingStatus> seatings;

    public ShowtimeSeating(Seat[] seats) {
        for (Seat seat: seats)
            seatings.put(seat, SeatingStatus.AVAILABLE);
    }

    public SeatingStatus getSeatingStatus(Seat seat) {
        SeatingStatus seatingStatus = seatings.get(seat);
        if (seatingStatus == null)
            return null; // TODO Error seat not found
        return seatingStatus;
    }

    public boolean isAvailable(Seat seat) {
        return getSeatingStatus(seat) == SeatingStatus.AVAILABLE;
    }

    public void setSeatingStatus(Seat seat, SeatingStatus seatingStatus) {
        if (!hasSeat(seat))
            return; // TODO Error seat not found
        seatings.put(seat, seatingStatus);
    }

    public ArrayList<Seat> getSeats(SeatingStatus status) {
        ArrayList<Seat> seats = new ArrayList<Seat>();
        for (SeatType seatType: SeatType.values()) {
            seats.addAll(getAvailableSeats(seatType));
        }
        return seats;
    }

    public ArrayList<Seat> getAvailableSeats(SeatType seatType) {
        ArrayList<Seat> availableSeats = new ArrayList<Seat>();
        for(Seat seat : seatings.keySet()) {
            if (seat.getType() == seatType && isAvailable(seat))
                availableSeats.add(seat);
        }
        return availableSeats;
    }

    public ArrayList<Seat> getSeats() {
        return new ArrayList<Seat>(seatings.keySet());
    }

    public boolean hasSeat(Seat seat) {
        return seatings.containsKey(seat);
    }
}
