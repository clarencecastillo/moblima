package model.cinema;

import java.io.Serializable;

/**
 Represents a cell in the cinema layout which can be either an empty space or a seat.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class Cell implements Serializable {

    /**
     * This cell's row character.
     */
    protected char row;

    /**
     * This cell's column number.
     */
    protected int column;

    /**
     * Creates a cell with the row character and column number
     * @param row This cell's row character.
     * @param column This row's column number.
     */
    public Cell(char row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets this cell's row character.
     * @return this cell's row character.
     */
    public char getRow() {
        return row;
    }

    /**
     * Gets this cell's colomn number.
     * @return this cell's colomn number..
     */
    public int getColumn() {
        return column;
    }

    /**
     * CHanges this cell's row character.
     * @param row this cell's new row character.
     */
    public void setRow(char row) {
        this.row = row;
    }

    /**
     * Changes this cell's column number.
     * @param column this cell's new column number.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Compares this cell to another.
     * @param obj The object to compare to.
     * @return true if and only if obj is instance of the cell and has the same charater and column number.
     */
    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || !(obj instanceof Cell))
            return false;
        Cell seat = (Cell)obj;
        return seat.row == row && seat.column == column;
    }
}
