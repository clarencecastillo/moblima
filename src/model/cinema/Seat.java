package model.cinema;

import java.io.Serializable;

public class Seat implements Serializable {

    private char row;
    private int column;
    private SeatType type;

    public Seat(char row, int column, SeatType type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
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
        return seat.row == row && seat.column == column && seat.type == type;
    }
}
