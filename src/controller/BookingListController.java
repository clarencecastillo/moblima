package controller;

import manager.BookingController;
import manager.UserController;
import model.booking.Booking;
import model.booking.Ticket;
import model.commons.User;
import view.*;

import java.util.ArrayList;

public class BookingListController extends Controller {

    private static BookingListController instance = new BookingListController();

    private Menu bookingListMenu;

    private BookingController bookingController;
    private UserController userManager;

    private BookingListController() {
        bookingController = BookingController.getInstance();
        userManager =  UserController.getInstance();
    }

    public static BookingListController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        bookingListMenu = new Menu();
        bookingListMenu.setTitle("Booking History");
        bookingListMenu.setContent("Please enter your mobile number.");
    }

    @Override
    public void onEnter(String[] arguments) {

        bookingListMenu.displayHeader();

        String mobile = bookingListMenu.getString("Mobile number");
        User user = userManager.findByMobile(mobile);

        ArrayList<Booking> bookings = user.getBookings();

        navigation.clearScreen();
        bookingListMenu.displayHeader();

        ArrayList<ViewItem> items = new ArrayList<>();
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            Ticket[] tickets = booking.getTickets();
            String[] seats = new String[tickets.length];
            for (int j = 0; j < tickets.length; j++) {
                seats[j] = tickets[i].getSeat().toString();
            }

            ViewItem bookingView = new ViewItem(booking.getId().toString());
            bookingView.setTitle(String.format("%s", booking.getShowtime().getMovie().getTitle()));
            bookingView.setContent(new String[] {
                "Cinema: " + booking.getShowtime().getCinema().getCode(),
                "Start Time: " + booking.getShowtime().getStartTime(),
                "Seats: " + String.join(",", seats)
            });
            items.add(bookingView);
        }


        bookingListMenu.setViewItems(items.toArray(new ViewItem[items.size()]));
        bookingListMenu.displayItems();
        bookingListMenu.getChoice();
    }
}
