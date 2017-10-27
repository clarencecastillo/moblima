package view;

import config.BookingConfig;
import config.PaymentConfig;
import exception.RejectedNavigationException;
import manager.BookingController;
import manager.PaymentController;
import manager.ShowtimeController;
import manager.UserController;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.TicketType;
import model.cinema.Seat;
import model.commons.User;
import model.transaction.Payment;
import model.transaction.Priceable;
import view.ui.*;

import java.util.*;

public class BookingMenuView extends MenuView {

    private User user;
    private Booking booking;
    private Hashtable<TicketType, Double> ticketTypePricing;

    private BookingMenuIntent intent;
    private AccessLevel accessLevel;

    private BookingController bookingController;
    private ShowtimeController showtimeController;
    private UserController userController;
    private PaymentController paymentController;

    public BookingMenuView(Navigation navigation) {
        super(navigation);
        this.bookingController = BookingController.getInstance();
        this.showtimeController = ShowtimeController.getInstance();
        this.userController = UserController.getInstance();
        this.paymentController = PaymentController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                break;
            case PUBLIC:
                break;
        }

        booking = bookingController.findById(UUID.fromString(args[0]));
        if (booking == null) {
            View.displayError("Booking not found!");
            Form.pressAnyKeyToContinue();
            throw new RejectedNavigationException();
        }

        Showtime showtime = booking.getShowtime();

        this.ticketTypePricing = new Hashtable<>();
        for (TicketType ticketType : showtimeController.getAvailableTicketTypes(showtime.getId()))
            ticketTypePricing.put(ticketType, showtimeController.getTicketTypePricing(showtime.getId(), ticketType));

        ArrayList<String> content = new ArrayList<>();
        this.intent = (BookingMenuIntent) intent;
        switch (this.intent) {
            case PRINT_TICKETS:
                Hashtable<TicketType, Integer> ticketTypesCount = booking.getTicketTypesCount();
                List<Seat> seats = booking.getSeats();
                int nextSeatIndex = 0;
                for (TicketType ticketType : ticketTypesCount.keySet())
                    for (int i = 0; i < ticketTypesCount.get(ticketType); i++) {
                        TicketView ticketView = new TicketView(showtime, seats.get(nextSeatIndex++),
                                ticketType, ticketTypePricing.get(ticketType));
                        ticketView.display();
                    }
                Form.pressAnyKeyToContinue();
                navigation.reload(accessLevel, BookingMenuIntent.VIEW_BOOKING, booking.getId().toString());
                break;
            case MAKE_PAYMENT:
                View.displayInformation("Please enter your personal information. You will be asked to " +
                        "enter your card details in the following section.");
                String firstName = Form.getString("First Name", 1);
                String lastName = Form.getString("Last Name", 1);
                String mobile = Form.getString("Mobile", 1);
                String email = Form.getString("Email", 1);

                user = userController.findByMobile(mobile);
                if (user == null)
                    user = userController.registerUser(firstName, lastName, mobile, email);

                View.displayInformation("Please enter your card details in order to complete your purchase.");
                Form.getString("Card Number");
                Form.getDate("Expiry Date", "MM/YYYY");
                Form.getString("Security Code");
                View.displayWarning("By proceeding to PAY, you hereby authorise the debit to your " +
                        "Card Account in favour of MOBLIMA PTE LTD");
                Form.pressAnyKeyToContinue();
                Payment payment = paymentController.makePayment(booking);
                View.displaySuccess("Payment Successful! Transaction ID: " + payment.getTransactionId());
                bookingController.confirmBooking(booking.getId(), user.getId());
                Form.pressAnyKeyToContinue();
                navigation.reload(accessLevel, BookingMenuIntent.VIEW_BOOKING, booking.getId().toString());
            case VIEW_BOOKING:
                setMenuItems(BookingMenuOption.PRINT_TICKETS);
                break;
            case VERIFY_BOOKING:
                setMenuItems(BookingMenuOption.CONFIRM_BOOKING,
                        BookingMenuOption.CANCEL_BOOKING);
                content.addAll(Arrays.asList(
                        "Please verify booking information. By proceeding to CONFIRM, you have confirmed " +
                                "your agreement to the terms and conditions of purchase. You also confirm that the " +
                                "tickets are purchased for persons permitted to watch the movie according to the " +
                                "movie ratings by MDA.",
                        " ",
                        "You agree that no refunds, exchanges, amendments or cancellations will be given if " +
                                "the movie ratings requirements are not met.",
                        " "
                ));
                break;
        }

        setTitle("Booking Information");
        if (intent == BookingMenuIntent.VIEW_BOOKING)
            content.addAll(Arrays.asList(new BookingView(booking).getContent()));
        else
            content.addAll(Arrays.asList(new BookingView(booking.getShowtime(),
                    booking.getSeats(), booking.getTicketTypesCount(), ticketTypePricing).getContent()));
        content.addAll(Arrays.asList(" ",
                "Booking Fee: " + String.format("$%.2f", BookingConfig.getBookingSurcharrge())));
        setContent(content.toArray(new String[content.size()]));
        addBackOption();
    }

    @Override
    public void onEnter() {
        displayTitle();
        displayContent();
        View.displayInformation(String.format("Total $%.2f (Inclusive of %.0f%% GST)",
                Priceable.getPriceWithGst(booking), PaymentConfig.getGst()*100));

        if (intent == BookingMenuIntent.VERIFY_BOOKING)
            View.displayWarning("Booking fee for all transaction is non-refundable. This fee covers the costs " +
                    "associated with administration, development and maintenance required on the software.");

        displayItems();
        String userInput = getChoice();
        if (userInput.equals(BACK)) {
            navigation.goBack(user != null ? 6 : 1);
        } else
            switch (BookingMenuOption.valueOf(userInput)) {
                case PRINT_TICKETS:
                    navigation.reload(accessLevel, BookingMenuIntent.PRINT_TICKETS, booking.getId().toString());
                    break;
                case CONFIRM_BOOKING:
                    navigation.reload(accessLevel, BookingMenuIntent.MAKE_PAYMENT, booking.getId().toString());
                    break;
                case CANCEL_BOOKING:
                    View.displayWarning("You are currently in the middle of booking confirmation. Are you sure " +
                            "you want to go back? All progress up to this point will be lost.");
                    switch (Form.getConfirmOption("Leave", "Cancel")) {
                        case CONFIRM:
                            bookingController.cancelBooking(booking.getId());
                            navigation.goBack(3);
                            break;
                        case CANCEL:
                            navigation.refresh();
                            break;
                    }
                    break;
            }

    }

    public enum BookingMenuIntent implements Intent {
        VIEW_BOOKING,
        VERIFY_BOOKING,
        MAKE_PAYMENT,
        PRINT_TICKETS
    }

    public enum BookingMenuOption implements EnumerableMenuOption {

        CONFIRM_BOOKING("Confirm"),
        CANCEL_BOOKING("Cancel"),
        PRINT_TICKETS("Print Tickets");

        private String description;
        BookingMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
