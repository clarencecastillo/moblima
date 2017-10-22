package view;

import config.BookingConfig;
import exception.InputOutOfBoundsException;
import exception.InputUnrecognisedException;
import view.ui.*;

import java.util.ArrayList;

public class BookingConfigListView extends ListView {

    private BookingConfig bookingConfig;

    public BookingConfigListView(Navigation navigation) {
        super(navigation);
        this.bookingConfig = BookingConfig.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        setTitle("Booking Config");
        setContent("Select the item to change. All monetary values are displayed in SGD.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        viewItems.add(new ViewItem("Maximum Seats Per Booking",
                BookingConfigMenuOption.MAX_SEATS_PER_BOOKING.toString(),
                String.format("%d seats", BookingConfig.getMaxSeatsPerBooking())));
        viewItems.add(new ViewItem("Minimum Days Before Open Booking",
                BookingConfigMenuOption.MIN_DAYS_BEFORE_OPEN_BOOKING.toString(),
                String.format("%d days", BookingConfig.getMinDaysBeforeOpenBooking())));
        viewItems.add(new ViewItem("Booking Fee",
                BookingConfigMenuOption.BOOKING_FEE.toString(),
                String.format("$%.2f", BookingConfig.getBookingSurcharrge())));
        viewItems.add(new ViewItem("Booking Changes Grace Period",
                BookingConfigMenuOption.BOOKING_CHANGE_GRACE_PERIOD.toString(),
                String.format("%d minutes", BookingConfig.getBookingChangesGraceMinutes())));
        viewItems.add(new ViewItem("Booking Changes Fee",
                BookingConfigMenuOption.BOOKING_CHANGE_FEE.toString(),
                String.format("$%.2f", BookingConfig.getBookingChangesSurcharge())));
        viewItems.add(new ViewItem("Minutes Before Closed Booking",
                BookingConfigMenuOption.MINS_BEFORE_CLOSED_BOOKING.toString(),
                String.format("%d minutes", BookingConfig.getMinutesBeforeClosedBooking())));
        setViewItems(viewItems.toArray(new ViewItem[viewItems.size()]));

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            View.displayInformation("Please enter new value. Enter pricing values in SGD.");
            while (true)
                try {
                    switch (BookingConfigMenuOption.valueOf(userChoice)) {
                        case MAX_SEATS_PER_BOOKING:
                            int newMaxSeatsPerBooking = Form.getInt("Enter new maximum seats per booking",
                                    0, 20);
                            bookingConfig.setMaxSeatsPerBooking(newMaxSeatsPerBooking);
                            break;
                        case MIN_DAYS_BEFORE_OPEN_BOOKING:
                            int newMinDaysBeforeOpenBooking = Form.getInt("Enter new minimum days " +
                                            "before open booking",
                                    0, 31);
                            bookingConfig.setMinDaysBeforeOpenBooking(newMinDaysBeforeOpenBooking);
                            break;
                        case BOOKING_FEE:
                            double newBookingFee = Form.getDouble("Enter new booking fee", 0, 10);
                            bookingConfig.setBookingSurcharge(newBookingFee);
                            break;
                        case BOOKING_CHANGE_GRACE_PERIOD:
                            int newBookingChangeGracePeriod = Form.getInt("Enter new booking change " +
                                    "grace period", 0, 360);
                            bookingConfig.setBookingChangesGraceMinutes(newBookingChangeGracePeriod);
                            break;
                        case BOOKING_CHANGE_FEE:
                            double newBookingChangeFee = Form.getDouble("Enter new booking change fee",
                                    0, 10);
                            bookingConfig.setBookingChangesSurcharge(newBookingChangeFee);
                            break;
                        case MINS_BEFORE_CLOSED_BOOKING:
                            int newMinutesBeforeClosedBooking  = Form.getInt("Enter new minutes before " +
                                    "closed booking", 0, 60);
                            bookingConfig.setMinutesBeforeClosedBooking(newMinutesBeforeClosedBooking);
                            break;
                    }
                    View.displaySuccess("Successfully changed booking config value!");
                    Form.pressAnyKeyToContinue();
                    break;
                } catch (InputOutOfBoundsException e) {
                    View.displayError("Invalid value! Please try again.");
                } catch (InputUnrecognisedException e) {
                    View.displayError("Unrecognised value! Please try again.");
                }
            navigation.reload();
        }
    }

    private enum BookingConfigMenuOption {
        MAX_SEATS_PER_BOOKING,
        MIN_DAYS_BEFORE_OPEN_BOOKING,
        BOOKING_FEE,
        BOOKING_CHANGE_GRACE_PERIOD,
        BOOKING_CHANGE_FEE,
        MINS_BEFORE_CLOSED_BOOKING;
    }
}
