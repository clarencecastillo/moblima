package controller;

import manager.BookingManager;
import manager.UserManager;
import model.booking.Booking;
import model.booking.Ticket;
import model.commons.User;
import view.*;

import java.util.ArrayList;

public class BookingListController extends Controller {

    private static BookingListController instance = new BookingListController();

    private ListMenu bookingListMenu;

    private BookingManager bookingManager;
    private UserManager userManager;

    private BookingListController() {
        bookingManager = BookingManager.getInstance();
        userManager =  UserManager.getInstance();
    }

    public static BookingListController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        bookingListMenu = new ListMenu();
        bookingListMenu.setTitle("Booking History");
        bookingListMenu.displayHeader();
    }

    @Override
    public void onLoad(String[] arguments) {
        String mobile = bookingListMenu.getString("Please enter your mobile number to log in");
        User user = userManager.findByMobile(mobile);

        ArrayList<Booking> bookings = user.getBookings();

        navigation.clearScreen();
        bookingListMenu.displayHeader();

        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            Ticket[] tickets = booking.getTickets();
            String[] seats = new String[tickets.length];
            for (int j = 0; j < tickets.length; j++) {
                seats[j] = tickets[i].getSeat().toString();
            }

            ListMenuItem bookingView = new ListMenuItem(i + 1);
            bookingView.setTitle(String.format("%s", booking.getShowtime().getMovie()));
            bookingView.setContent(new String[] {
                "Cinema: " + booking.getShowtime().getCinema(),
                "Start Time: " + booking.getShowtime().getStartTime(),
                "Seats: " + String.join(",", seats)
            });
            items.add(bookingView);
        }


        bookingListMenu.setMenuItems(items.toArray(new ListMenuItem[items.size()]));
        bookingListMenu.displayMenuItemsWithBack();
        bookingListMenu.getChoice();
    }
}
