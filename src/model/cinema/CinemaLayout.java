package model.cinema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 Represents the cinema layout of a cinema.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class CinemaLayout implements Serializable {

    /**
     * A hashtable of the row character as key and array of cells in that row as the value.
     */
    private Hashtable<Character,Cell[]> layout;

    /**
     * The maximum number of columns in the layout.
     */
    private int maxColumn;

    /**
     * The maximum number of rows in the layout.
     */
    private char maxRow;

    /**
     * The array of cells that are seats instead of empty space.
     */
    private Seat[] seats;

    /**
     * Creates a cinema layout with the given array of seats, maximum number of columns and maximum number of rows.
     * @param seats an given array of this cinema layout's seats
     * @param maxColumn this layout's given maximum number of columns.
     * @param maxRow this layout's given maximum number of row.
     */
    public CinemaLayout(Seat[] seats, int maxColumn, char maxRow) {
        this.layout = new Hashtable<Character,Cell[]>();
        this.maxColumn = maxColumn;
        this.maxRow = maxRow;
        this.seats = seats;

        // Create a matrix of cells according to the maximum number of columns and maximum number of row.
        for (char row = 'A'; row <= maxRow; row++) {
            Cell[] rowCells = new Cell[maxColumn];
            for (int column = 1; column <= maxColumn; column++)
                rowCells[column - 1] = new Cell(row, column);
            this.layout.put(row, rowCells);
        }

        // Change a cell to a seat where specified
        for (Seat seat: seats)
            this.layout.get(seat.row)[seat.column - 1] = seat;
    }

    /**
     * Gets this layout in the form of a hashtable with the row character as the key and array of cells in that row as the value.
     * @return this layout in the form of a hashtable with the row character as the key and array of cells in that row as the value.
     */
    public Hashtable<Character, Cell[]> getLayout() {
        return layout;
    }

    /**
     * Gets this layout's maximum column.
     * @return this layout's maximum column.
     */
    public int getMaxColumn() {
        return maxColumn;
    }

    /**
     * Gets this layout's maximum row.
     * @return this layout's maximum row.
     */
    public char getMaxRow() {
        return maxRow;
    }

    /**
     * Gets this layout's array of seats.
     * @return
     */
    public Seat[] getSeats() {
        return seats;
    }

    /**
     * Changes this layout's maximum column.
     * @param maxColumn this layout's new maximum column.
     */
    public void setMaxColumn(int maxColumn) { this.maxColumn = maxColumn; }

    /**
     * Changes this layout's maximum row.
     * @param maxRow this layout's new maximum row.
     */
    public void setMaxRow(char maxRow) {
        this.maxRow = maxRow;
    }

    /**
     * Changes this layout's array of seats.
     * @param seats this layout's new array of seats.
     */
    public void setSeats(Seat[] seats) {
        this.seats = seats;
    }



}
