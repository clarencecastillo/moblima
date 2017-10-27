package view;

import config.BookingConfig;
import exception.ExceedBookingSeatException;
import exception.RejectedNavigationException;
import manager.BookingController;
import manager.ShowtimeController;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.TicketType;
import util.Utilities;
import view.ui.*;

import java.util.Hashtable;
import java.util.UUID;
import java.util.stream.Collectors;

public class TicketListView extends ListView {

    private Booking booking;
    private Hashtable<TicketType, Integer> ticketTypeCount;
    private Hashtable<TicketType, Double> ticketTypePricing;

    private AccessLevel accessLevel;

    private ShowtimeController showtimeController;
    private BookingController bookingController;

    public TicketListView(Navigation navigation) {
        super(navigation);
        this.showtimeController = ShowtimeController.getInstance();
        this.bookingController = BookingController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {
        this.accessLevel = accessLevel;

        booking = bookingController.findById(UUID.fromString(args[0]));
        if (booking == null) {
            View.displayError("Booking not found!");
            Form.pressAnyKeyToContinue();
            throw new RejectedNavigationException();
        }

        Showtime showtime = booking.getShowtime();
        this.ticketTypeCount = new Hashtable<>();
        this.ticketTypePricing = new Hashtable<>();
        for (TicketType ticketType : showtimeController.getAvailableTicketTypes(showtime.getId())) {
            ticketTypeCount.put(ticketType, 0);
            ticketTypePricing.put(ticketType, showtimeController.getTicketTypePricing(showtime, ticketType));
        }

        setTitle("Ticket Selection");
        setContent(showtime.getMovie().toString(showtime.isNoFreePasses()),
                "Showing on " + Utilities.toFormat(showtime.getStartTime(), DATE_DISPLAY_FORMAT),
                "Cinema: " + showtime.getCineplex().getName() + " Hall " + showtime.getCinema().getCode(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)),
                "-------",
                "Select the number and type of tickets you wish to buy. " +
                "Please note seats are reserved on a best available basis. " +
                "You can buy a maximum of 10 tickets per transaction.");
        setMenuItems(TicketListOption.values());
        addBackOption();
    }

    @Override
    public void onEnter() {
        int totalCount = ticketTypeCount.values().stream().mapToInt(Integer::intValue).sum();
        setViewItems(ticketTypeCount.keySet().stream().map(ticketType ->
                new ViewItem(ticketType.toString(), ticketType.name(),
                        "Price " + String.format("$%.2f", ticketTypePricing.get(ticketType)),
                        "Quantity: " + ticketTypeCount.get(ticketType)
                        )).collect(Collectors.toList()));

        display();

        double subtotal = 0.0;
        for (TicketType ticketType : ticketTypeCount.keySet())
            subtotal += ticketTypeCount.get(ticketType) * ticketTypePricing.get(ticketType);
        View.displayInformation("Subtotal: " + String.format("$%.2f", subtotal));

        String userInput = getChoice();
        if (userInput.equals(BACK)) {
            if (totalCount > 0) {
                View.displayWarning("You have " + totalCount + " selected ticket(s) pending for booking. Are you sure " +
                        "you want to go back? All progress up to this point will be lost.");
                switch (Form.getConfirmOption("Leave", "Cancel")) {
                    case CONFIRM:
                        bookingController.cancelBooking(booking.getId());
                        navigation.goBack();
                        break;
                    case CANCEL:
                        navigation.refresh();
                        break;
                }
            } else
                navigation.goBack();
        } else
            try {
                switch (TicketListOption.valueOf(userInput)) {
                    case SELECT_SEATS:
                        if (totalCount == 0) {
                            View.displayError("A minimum of 1 seat is required to create a booking.");
                            Form.pressAnyKeyToContinue();
                            navigation.refresh();
                        } else {
                            try {
                                bookingController.selectTicketType(booking.getId(), ticketTypeCount);
                                navigation.goTo(new CinemaMenuView(navigation), accessLevel, booking.getId().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                                View.displayError("Cannot select ticket types at this time.");
                                Form.pressAnyKeyToContinue();
                                navigation.refresh();
                            }
                        }
                        break;
                }
            } catch (IllegalArgumentException e) {

                TicketType ticketType = TicketType.valueOf(userInput);
                int currentCount = ticketTypeCount.get(ticketType);

                int changeValue = 0;
                if (totalCount == BookingConfig.getMaxSeatsPerBooking()) {
                    if (currentCount == 0) {
                        View.displayError("Maximum number of seats per booking reached!");
                        Form.pressAnyKeyToContinue();
                    } else
                        changeValue = -1;
                } else if (currentCount > 0) {
                    String change = Form.getOption("Option",
                            new GenericMenuOption("Add Seat", "INCREMENT"),
                            new GenericMenuOption("Remove Seat", "DECREMENT"));
                    changeValue = change.equals("DECREMENT") ? -1 : 1;
                } else
                    changeValue = 1;
                ticketTypeCount.put(ticketType, currentCount + changeValue);
                navigation.refresh();
            }

    }

    private enum TicketListOption implements EnumerableMenuOption {

        SELECT_SEATS("Select Seats");

        private String description;

        TicketListOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
