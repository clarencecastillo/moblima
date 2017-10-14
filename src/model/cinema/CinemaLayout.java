package model.cinema;

import java.io.Serializable;
import java.util.ArrayList;

public class CinemaLayout implements Serializable {

    private static boolean SCREEN_TOP = true;

    private ArrayList<Seat> seats;
    private int maxColumn;
    private char maxRow;

    public CinemaLayout(ArrayList<Seat> seats, int maxColumn, char maxRow) {
        this.seats = seats;
        this.maxColumn = maxColumn;
        this.maxRow = maxRow;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public char getMaxRow() {
        return maxRow;
    }
}
