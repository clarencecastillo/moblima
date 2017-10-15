package model.cinema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class CinemaLayout implements Serializable {

    private Hashtable<Character,Cell[]> layout;
    private int maxColumn;
    private char maxRow;
    private Seat[] seats;

    public CinemaLayout(Seat[] seats, int maxColumn, char maxRow) {
        this.layout = new Hashtable<Character,Cell[]>();
        this.maxColumn = maxColumn;
        this.maxRow = maxRow;

        // Create a matrix of cells
        for (char row = 'A'; row <= maxRow; row++) {
            Cell[] rowCells = new Cell[maxColumn];
            for (int column = 1; column <= maxColumn; column++)
                rowCells[column - 1] = new Cell(row, column);
            this.layout.put(row, rowCells);
        }

        // Change cell to seat where specified
        for (Seat seat: seats)
            this.layout.get(seat.row)[seat.column - 1] = seat;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public Hashtable<Character, Cell[]> getLayout() {
        return layout;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public char getMaxRow() {
        return maxRow;
    }


}
