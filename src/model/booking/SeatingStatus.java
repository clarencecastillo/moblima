package model.booking;

public enum SeatingStatus {

    AVAILABLE(" "),
    TAKEN("X"),
    MAINTENANCE("!"),
    RESERVED("-");

    private String icon;

    SeatingStatus(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return icon;
    }
}
