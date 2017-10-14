package model.booking;

import config.TicketConfig;
import model.transaction.Priceable;

public enum TicketType implements Priceable {

    STUDENT("Student Ticket"),
    SENIOR_CITIZEN("Senior Citizen Ticket"),
    STANDARD("Standard Ticket"),
    PEAK("Fri - Sun, Eve of PH & PH Ticket");

    private String name;

    TicketType(String name) {
        this.name = name;
    }

    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
