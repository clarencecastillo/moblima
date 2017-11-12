package view;

import config.BookingConfig;
import controller.BookingController;
import controller.CinemaController;
import controller.ShowtimeController;
import exception.IllegalActionException;
import exception.RejectedNavigationException;
import exception.UnauthorisedNavigationException;
import model.booking.*;
import model.cineplex.Cinema;
import model.commons.Language;
import util.Utilities;
import view.ui.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This moblima.view displays the user interface for the user to select ticket types during booking.
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
    private CinemaController cinemaController;

    public TicketTypeListView(Navigation navigation) {
        super(navigation);
        this.showtimeController = ShowtimeController.getInstance();
        this.bookingController = BookingController.getInstance();
        this.cinemaController = CinemaController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        switch ((TicketTypeListIntent) intent) {
            case VIEW_TICKET_TYPES:
                break;
            case UPDATE_SHOWTIME:

                if (accessLevel != AccessLevel.ADMINISTRATOR)
                    throw new UnauthorisedNavigationException();

                View.displayInformation("Please enter updated showtime details.");
                List<Cinema> cinemas = showtime.getCineplex().getCinemas();
                Cinema cinema = cinemaController.findById(UUID.fromString(Form.getOption("Cinema",
                        cinemas.stream().map(cineplexCinema ->
                                new GenericMenuOption("Hall " + cineplexCinema.getCode() + "  " +
                                        cineplexCinema.getType(), cineplexCinema.getId().toString()))
                                .toArray(GenericMenuOption[]::new))));
                Language language = Language.valueOf(Form.getOption("Language", Language.values()));
                int numberOfSubtitles = Form.getIntWithMin("Number of Subtitles", 0);
                while (numberOfSubtitles > Language.values().length) {
                    View.displayError("There are only " + Language.values().length + " languages available.");
                    numberOfSubtitles = Form.getIntWithMin("Number of Subtitles", 0);
                }
                Language[] subtitles = new Language[numberOfSubtitles];
                for (int i = 0; i < numberOfSubtitles; i++) {
                    Language subtitle = Language.valueOf(Form.getOption("Subtitle " + (i + 1), Language.values()));
                    while (Arrays.asList(subtitles).contains(subtitle)) {
                        View.displayError(subtitle + " is already set.");
                        subtitle = Language.valueOf(Form.getOption("Subtitle " + (i + 1), Language.values()));
                    }
                    subtitles[i] = subtitle;
                }

                Date startTime;
                do {
                    startTime = Form.getDate("Start Time", "dd/MM/yyyy HH:mm");
                    if (startTime.before(new Date()))
                        View.displayError("The specified time has already passed.");
                    else
                        break;
                } while (true);

                boolean noFreePasses = Form.getBoolean("No Free Passes");
                try {
                    showtimeController.changeShowtimeDetails(showtime.getId(), cinema.getId(), language,
                            startTime, noFreePasses, subtitles);
                    View.displaySuccess("Successfully created showtime!");
                } catch (IllegalActionException e) {
                    View.displayError(e.getMessage());
                }
                Form.pressAnyKeyToContinue();
                break;
        }

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
                "Cinema: " + showtime.getCineplex().getName() + " " + showtime.getCinema(),
                "Language: " + showtime.getLanguage(),
                "Subtitles: " + String.join(",", showtime.getSubtitles().stream()
                        .map(String::valueOf).toArray(String[]::new)));

        switch (accessLevel) {
            case ADMINISTRATOR:
                setTitle("Showtime Sales Summary");
                ArrayList<TicketTypeListOption> options = new ArrayList<>();
                options.add(TicketTypeListOption.VIEW_SEATING);
                if (!showtime.hasConfirmedBooking() && showtime.getStatus() != ShowtimeStatus.CANCELLED)
                    options.addAll(Arrays.asList(TicketTypeListOption.CANCEL_SHOWTIME, TicketTypeListOption.UPDATE_SHOWTIME));
                setMenuItems(options.toArray(new TicketTypeListOption[options.size()]));
                content.add("Status: " + showtime.getStatus());
                break;
            case PUBLIC:
                setTitle("Ticket Selection");
                setMenuItems(TicketTypeListOption.SELECT_SEATS);
                content.addAll(Arrays.asList(
                        "-------",
                        "Select the number and type of tickets you wish to buy. " +
                                "Please note seats are reserved on a best available basis. " +
                                "You can buy a maximum of 10 tickets per transaction."
                ));
                break;
        }

        this.ticketTypeCount = new Hashtable<>();
        for (TicketType ticketType : showtimeController.getAvailableTicketTypes(showtime.getId()))
            ticketTypeCount.put(ticketType, 0);

        addBackOption();
    }

    @Override
    public void onEnter() {

        if (accessLevel == AccessLevel.ADMINISTRATOR) {
            for (TicketType ticketType : showtimeController.getAvailableTicketTypes(showtime.getId()))
                ticketTypeCount.put(ticketType, 0);
            for (Booking booking : showtime.getBookings())
                if (booking.getStatus() == BookingStatus.CONFIRMED) {
                    Hashtable<TicketType, Integer> bookingTicketTypesCount = booking.getTicketTypesCount();
                    for (TicketType ticketType : bookingTicketTypesCount.keySet()) {
                        int currentCount = ticketTypeCount.get(ticketType);
                        ticketTypeCount.put(ticketType, currentCount + bookingTicketTypesCount.get(ticketType));
                    }

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
                switch (TicketTypeListOption.valueOf(userInput)) {
                    case UPDATE_SHOWTIME:
                        navigation.reload(accessLevel, TicketTypeListIntent.UPDATE_SHOWTIME,
                                showtime.getId().toString());
                        break;
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
                                    navigation.reload(accessLevel, TicketTypeListIntent.VIEW_TICKET_TYPES,
                                            showtime.getId().toString());
                                    break;
                                case CANCEL:
                                    navigation.refresh();
                                    break;
                            }
                        } else {
                            showtimeController.cancelShowtime(showtime.getId());
                            View.displaySuccess("Showtime successfully canceled!");
                            Form.pressAnyKeyToContinue();
                            navigation.reload(accessLevel, TicketTypeListIntent.VIEW_TICKET_TYPES,
                                    showtime.getId().toString());
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
                    for (TicketType tType : ticketTypeCount.keySet())
                        ticketTypeCount.put(tType, 0);
                    navigation.refresh();
                }
            }

    }

    public enum TicketTypeListIntent implements Intent {

        VIEW_TICKET_TYPES,
        UPDATE_SHOWTIME
    }

    private enum TicketTypeListOption implements EnumerableMenuOption {

        SELECT_SEATS("Select Seats"),
        CANCEL_SHOWTIME("Cancel Showtime"),
        VIEW_SEATING("Check Seating"),
        UPDATE_SHOWTIME("Update Showtime");

        private String description;

        TicketTypeListOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
