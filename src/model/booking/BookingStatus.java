package model.booking;

public enum BookingStatus {

    CANCELLED("Cancelled"),
    CONFIRMED("Confirmed"),
    IN_PROGRESS("In Progress");

    private String name;

    BookingStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
