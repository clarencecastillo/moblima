package model.booking;

import config.HolidayConfig;
import config.TicketConfig;
import java.util.Calendar;
import java.util.Date;
import model.cinema.CinemaType;
import model.transaction.Priceable;
import util.Utilities;

public enum TicketType implements Priceable {

    STUDENT("Student Ticket"),
    SENIOR_CITIZEN("Senior Citizen Ticket"),
    STANDARD("Standard Ticket"),
    PEAK("Fri - Sun, Eve of PH & PH Ticket");

    private String string;

    TicketType(String string) {
        this.string = string;
    }

    public double getPrice() {
        return TicketConfig.getPriceableRate(this);
    }

    @Override
    public String toString() {
        return string;
    }

    public boolean isValidFor(Booking booking) {

        CinemaType bookingCinemaType = booking.getShowtime().getCinema().getType();

        if (!bookingCinemaType.isAvailable(this))
            return false; // TODO Ticket type not available for this cinema

        if (this == PEAK) {
            Date movieDate = booking.getShowtime().getStartTime();
            if (!HolidayConfig.isHoliday(movieDate) &&
                !(Utilities.dateFallsOn(movieDate, Calendar.FRIDAY) ||
                  Utilities.dateFallsOn(movieDate, Calendar.SATURDAY) ||
                  Utilities.dateFallsOn(movieDate, Calendar.SUNDAY)))
                return false;
        }

        return true;
    }
}
