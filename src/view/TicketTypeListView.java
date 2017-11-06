package view;

import config.BookingConfig;
import exception.RejectedNavigationException;
import manager.BookingController;
import manager.ShowtimeController;
import model.booking.*;
import util.Utilities;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This view displays the user interface for the user to select ticket types during booking.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class TicketTypeListView extends ListView {

    private Booking booking;
    private Showtime showtime;
    private Hashtable<TicketType, Integer> ticketTypeCount;

    private AccessLevel accessLevel;

    private ShowtimeController showtimeController;
    private BookingController bookingController;

    public TicketTypeListView(Navigation navigation) {
        super(navigation);
        this.showtimeController = ShowtimeController.getInstance();
        this.bookingController = BookingController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                showtime = showtimeController.findById(UUID.fromString(args[0]));
                if (showtime == null) {
                    View.displayError("Showtime not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }
                break;
            case PUBLIC:
                booking = bookingController.findById(UUID.fromString(args[0]));
                if (booking == null) {
                    View.displayError("Booking not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }
                showtime = booking.getShowtime();
                break;
        }

        setContent(showtime.getMovie().toString(showtime.isNoFreePasses()),
                "Showing on " + Utilities.toFormat(showtime.getStartTime(), DATE_DISPLAY_FORMAT + " HH:mm"),
                "Cinema: " + showtime.getCineplex().getName() + " Hall " + showtime.getCinema().getCode(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)));

        switch (accessLevel) {
            case ADMINISTRATOR:
                setTitle("Showtime Sales Summary");
                ArrayList<TicketListOption> options = new ArrayList<>();
                options.add(TicketListOption.VIEW_SEATING);
                if (showtime.getStatus() == ShowtimeStatus.OPEN_BOOKING)
                    options.add(TicketListOption.CANCEL_SHOWTIME);
                setMenuItems(options.toArray(new TicketListOption[options.size()]));
                content.add("Status: " + showtime.getStatus());
                break;
            case PUBLIC:
                setTitle("Ticket Selection");
                setMenuItems(TicketListOption.SELECT_SEATS);
                content.addAll(Arrays.asList(
                        "-------",
                        "Select the number and type of tickets you wish to buy. " +
                                "Please note seats are reserved on a best available basis. " +
                                "You can buy a maximum of 10 tickets per transaction."
                ));
                break;
        }
        addBackOption();
    }

    @Override
    public void onEnter() {

        this.ticketTypeCount = new Hashtable<>();
        for (TicketType ticketType : showtimeController.getAvailableTicketTypes(showtime.getId()))
            ticketTypeCount.put(ticketType, 0);

        if (accessLevel == AccessLevel.ADMINISTRATOR)
            for (Booking booking : showtime.getBookings())
                if (booking.getStatus() == BookingStatus.CONFIRMED) {
                    Hashtable<TicketType, Integer> bookingTicketTypesCount = booking.getTicketTypesCount();
                    for (TicketType ticketType : bookingTicketTypesCount.keySet()) {
                        int currentCount = ticketTypeCount.get(ticketType);
                        ticketTypeCount.put(ticketType, currentCount + bookingTicketTypesCount.get(ticketType));
                    }

                }

        int totalCount = ticketTypeCount.values().stream().mapToInt(Integer::intValue).sum();
        setViewItems(ticketTypeCount.keySet().stream().map(ticketType ->
                new ViewItem(new TicketTypeView(ticketType, showtime.getTicketTypePricing(ticketType),
                        ticketTypeCount.get(ticketType)), ticketType.name())).collect(Collectors.toList()));
        display();

        double subtotal = 0.0;
        for (TicketType ticketType : ticketTypeCount.keySet())
            subtotal += ticketTypeCount.get(ticketType) * showtime.getTicketTypePricing(ticketType);

        View.displayInformation((accessLevel == AccessLevel.ADMINISTRATOR ? "Sales Total" : "Subtotal")
                + ": " + String.format("$%.2f", subtotal));

        String userInput = getChoice();
        if (userInput.equals(BACK)) {
            if (accessLevel == AccessLevel.PUBLIC && totalCount > 0) {
                View.displayWarning("You have " + totalCount + " selected ticket(s) pending for booking. " +
                        "Are you sure you want to go back? All progress up to this point will be lost.");
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
                    case CANCEL_SHOWTIME:
                        if (totalCount > 0) {
                            View.displayWarning("There are " + totalCount + " confirmed tickets for this showtime. " +
                                    "Are you sure you want to cancel?");
                            switch (Form.getConfirmOption("Yes", "No")) {
                                case CONFIRM:
                                    showtimeController.cancelShowtime(showtime.getId());
                                    View.displaySuccess("Showtime successfully canceled!");
                                    Form.pressAnyKeyToContinue();
                                    navigation.reload(accessLevel, showtime.getId().toString());
                                    break;
                                case CANCEL:
                                    navigation.refresh();
                                    break;
                            }
                        } else {
                            showtimeController.cancelShowtime(showtime.getId());
                            View.displaySuccess("Showtime successfully canceled!");
                            Form.pressAnyKeyToContinue();
                            navigation.reload(accessLevel, showtime.getId().toString());
                        }
                        break;
                    case VIEW_SEATING:
                        navigation.goTo(new CinemaMenuView(navigation), accessLevel, showtime.getId().toString());
                        break;
                }
            } catch (IllegalArgumentException e) {

                TicketType ticketType = TicketType.valueOf(userInput);
                int currentCount = ticketTypeCount.get(ticketType);
                if (accessLevel == AccessLevel.PUBLIC) {
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
                } else {
                    double sales = currentCount * showtime.getTicketTypePricing(ticketType);
                    View.displayInformation("Total Sales for " + ticketType + ": " + String.format("$%.2f", sales));
                    Form.pressAnyKeyToContinue();
                    navigation.refresh();
                }
            }

    }

    private enum TicketListOption implements EnumerableMenuOption {

        SELECT_SEATS("Select Seats"),
        CANCEL_SHOWTIME("Cancel Showtime"),
        VIEW_SEATING("Check Seating");

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
