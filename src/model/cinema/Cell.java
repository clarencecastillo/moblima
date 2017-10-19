package model.cinema;

import java.io.Serializable;

public class Cell implements Serializable {

    protected char row;
    protected int column;

    public Cell(char row, int column) {
        this.row = row;
        this.column = column;
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

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || !(obj instanceof Cell))
            return false;
        Cell seat = (Cell)obj;
        return seat.row == row && seat.column == column;
    }
}
