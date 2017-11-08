package view;

import controller.BookingController;
import controller.UserController;
import exception.RejectedNavigationException;
import model.booking.Booking;
import model.commons.User;
import view.ui.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
/**
 * This moblima.view displays the user interface for the user to check booking history.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class BookingListView extends ListView {

    private UserController userController;

    private User user;
    private List<Booking> bookings;

    private AccessLevel accessLevel;

    private BookingController bookingController;

    public BookingListView(Navigation navigation) {
        super(navigation);
        this.userController = UserController.getInstance();
        this.bookingController = BookingController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {
        setTitle("Booking History");

        this.accessLevel = accessLevel;
        switch (accessLevel) {

            case ADMINISTRATOR:
                bookings = bookingController.getList();
                break;
            case PUBLIC:
                View.displayInformation("Please enter your mobile number.");
                String mobileNumber = Form.getString("Mobile number");
                user = userController.findByMobile(mobileNumber);
                if (user == null) {
                    View.displayError("User with mobile '" + mobileNumber + "' not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }
                bookings = user.getBookings();
                break;
        }

        setContent("Displaying " + bookings.size() + " booking item(s).");
        addBackOption();
        setViewItems(bookings.stream().map(
                booking -> new ViewItem(new BookingView(booking.getShowtime(), booking.getSeats()),
                        booking.getId().toString(),
                        (int) TimeUnit.MILLISECONDS.toSeconds(booking.getPayment().getDate().getTime())))
                .collect(Collectors.toList()));
    }

    @Override
    public void onEnter() {
        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else {
            navigation.goTo(new BookingMenuView(navigation), accessLevel,
                    BookingMenuView.BookingMenuIntent.VIEW_BOOKING, userInput);
        }
    }
}
