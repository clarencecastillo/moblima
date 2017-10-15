package model.cinema;

import java.io.Serializable;

public class Seat extends Cell {

    private SeatType type;

    public Seat(char row, int column, SeatType type) {
        super(row,column);
        this.type = type;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Seat.class))
            return false;
        Seat seat = (Seat)obj;
        return super.equals(seat) && seat.type == type;
    }
}
