package view;

import config.BookingConfig;
import exception.UnauthorisedNavigationException;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This view displays the user interface for the user to configure booking settings.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class BookingConfigListView extends ListView {

    private BookingConfig bookingConfig;

    public BookingConfigListView(Navigation navigation) {
        super(navigation);
        this.bookingConfig = BookingConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Booking Config");
        setContent("Select the item to change. All monetary values are displayed in SGD.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        viewItems.add(new ViewItem("Maximum Seats Per Booking",
                String.format("%d seats", BookingConfig.getMaxSeatsPerBooking()),
                BookingConfigListOption.MAX_SEATS_PER_BOOKING.toString()));
        viewItems.add(new ViewItem("Minimum Days Before Open Booking",
                String.format("%d days", BookingConfig.getDaysBeforeOpenBooking()),
                BookingConfigListOption.MIN_DAYS_BEFORE_OPEN_BOOKING.toString()));
        viewItems.add(new ViewItem("Booking Fee",
                String.format("$%.2f", BookingConfig.getBookingSurcharrge()),
                BookingConfigListOption.BOOKING_FEE.toString()));
//        viewItems.add(new ViewItem("Booking Changes Grace Period",
//                BookingConfigMenuOption.BOOKING_CHANGE_GRACE_PERIOD.toString(),
//                String.format("%d minutes", BookingConfig.getBookingChangesGraceMinutes())));
//        viewItems.add(new ViewItem("Booking Changes Fee",
//                BookingConfigMenuOption.BOOKING_CHANGE_FEE.toString(),
//                String.format("$%.2f", BookingConfig.getBookingChangesSurcharge())));
        viewItems.add(new ViewItem("Minutes Before Closed Booking",
                String.format("%d minutes", BookingConfig.getMinutesBeforeClosedBooking()),
                BookingConfigListOption.MINS_BEFORE_CLOSED_BOOKING.toString()));
        viewItems.add(new ViewItem("Buffer Minutes After Showtime",
                String.format("%d minutes", BookingConfig.getBufferMinutesAfterShowtime()),
                BookingConfigListOption.BUFFER_MINUTES_AFTER_SHOWTIME.toString()));
        setViewItems(viewItems);

        display();

        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            View.displayInformation("Please enter new value. Enter pricing values in SGD.");
            switch (BookingConfigListOption.valueOf(userChoice)) {
                case MAX_SEATS_PER_BOOKING:
                    int newMaxSeatsPerBooking = Form
                            .getIntWithMin("Enter new maximum seats per booking", 0);
                    bookingConfig.setMaxSeatsPerBooking(newMaxSeatsPerBooking);
                    break;
                case MIN_DAYS_BEFORE_OPEN_BOOKING:
                    int newMinDaysBeforeOpenBooking = Form
                            .getIntWithMin("Enter new minimum days before open booking", 0);
                    bookingConfig.setMinDaysBeforeOpenBooking(newMinDaysBeforeOpenBooking);
                    break;
                case BOOKING_FEE:
                    double newBookingFee = Form.getDoubleWithMin("Enter new booking fee", 0);
                    bookingConfig.setBookingSurcharge(newBookingFee);
                    break;
                case BUFFER_MINUTES_AFTER_SHOWTIME:
                    int newBufferMinutesAfterShowtime = Form
                            .getIntWithMin("Enter new buffer minutes after showtime", 0);
                    bookingConfig.setBufferMinutesAfterShowtime(newBufferMinutesAfterShowtime);
                    break;
//                case BOOKING_CHANGE_GRACE_PERIOD:
//                    int newBookingChangeGracePeriod = Form
//                            .getIntWithMin("Enter new booking change grace period", 0);
//                    bookingConfig.setBookingChangesGraceMinutes(newBookingChangeGracePeriod);
//                    break;
//                case BOOKING_CHANGE_FEE:
//                    double newBookingChangeFee = Form.getDoubleWithMin("Enter new booking change fee", 0);
//                    bookingConfig.setBookingChangesSurcharge(newBookingChangeFee);
//                    break;
                case MINS_BEFORE_CLOSED_BOOKING:
                    int newMinutesBeforeClosedBooking = Form
                            .getIntWithMin("Enter new minutes before closed booking", 0);
                    bookingConfig.setMinutesBeforeClosedBooking(newMinutesBeforeClosedBooking);
                    break;
            }
            View.displaySuccess("Successfully changed booking config value!");
            Form.pressAnyKeyToContinue();
            navigation.reload(AccessLevel.ADMINISTRATOR);
        }
    }

    private enum BookingConfigListOption {
        MAX_SEATS_PER_BOOKING,
        MIN_DAYS_BEFORE_OPEN_BOOKING,
        BOOKING_FEE,
        BOOKING_CHANGE_GRACE_PERIOD,
        BOOKING_CHANGE_FEE,
        MINS_BEFORE_CLOSED_BOOKING,
        BUFFER_MINUTES_AFTER_SHOWTIME
    }
}
