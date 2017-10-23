package model.cinema;

public enum SeatType {

    SINGLE("Single"),
    COUPLE("Couple"),
    HANDICAP("Handicap");

    private String string;

    SeatType(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
