package view;

import controller.BookingController;
import controller.ShowtimeController;
import exception.RejectedNavigationException;
import model.booking.Booking;
import model.booking.Showtime;
import model.booking.ShowtimeSeating;
import model.cineplex.CinemaLayout;
import model.cineplex.Seat;
import view.ui.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This moblima.view displays the user interface for the user to select seats.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class CinemaMenuView extends MenuView {

    private Booking booking;
    private Showtime showtime;
    private CinemaLayout cinemaLayout;
    private ShowtimeSeating showtimeSeating;
    private ArrayList<Seat> selectedSeats;
    private int numberOfSeats;

    private AccessLevel accessLevel;
    private BookingController bookingController;
    private ShowtimeController showtimeController;

    public CinemaMenuView(Navigation navigation) {
        super(navigation);
        this.bookingController = BookingController.getInstance();
        this.showtimeController = ShowtimeController.getInstance();
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

                setTitle("Showtime Seating");
                break;
            case PUBLIC:
                booking = bookingController.findById(UUID.fromString(args[0]));
                if (booking == null) {
                    View.displayError("Booking not found!");
                    Form.pressAnyKeyToContinue();
                    throw new RejectedNavigationException();
                }
                showtime = booking.getShowtime();
                numberOfSeats = booking.getTotalTicketsCount();

                setTitle("Seat Selection");
                setMenuItems(CinemaMenuOption.values());
                selectedSeats = new ArrayList<>();
                break;
        }

        showtimeSeating = showtime.getSeating();
        cinemaLayout = showtime.getCinema().getLayout();
        addBackOption();
    }

    @Override
    public void onEnter() {
        setContent(new CinemaView(showtimeSeating, cinemaLayout, selectedSeats).getContent());
        display();

        if (accessLevel == AccessLevel.PUBLIC)
            View.displayInformation("Seats to Choose: " + (numberOfSeats - selectedSeats.size()));
        else
            View.displayInformation("Seats occupied: " + showtime.getBookings().stream()
                    .map(Booking::getTotalTicketsCount).mapToInt(Integer::intValue).sum());

        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (CinemaMenuOption.valueOf(userChoice)) {
                case CHOOSE_SEAT:
                    View.displayInformation("Please enter your seat selection.");
                    char row = Form.getChar("Row", 'A', cinemaLayout.getMaxRow());
                    int column = Form.getInt("Column", 1, cinemaLayout.getMaxColumn());
                    Seat seat = showtimeSeating.getSeatAt(row, column);
                    if (seat == null || !showtimeSeating.isAvailable(seat)) {
                        View.displayError("Selected seat is not available!");
                        Form.pressAnyKeyToContinue();
                        navigation.refresh();
                    } else if (selectedSeats.contains(seat)) {
                        View.displayError("You have already selected this seat!");
                        Form.pressAnyKeyToContinue();
                        navigation.refresh();
                    } else {
                        if (selectedSeats.size() == numberOfSeats)
                            selectedSeats.remove(0);
                        selectedSeats.add(seat);
                        View.displaySuccess("Successfully selected seat!");
                        Form.pressAnyKeyToContinue();
                        navigation.refresh();
                    }
                    break;
                case PROCEED_WITH_BOOKING:
                    if (selectedSeats.size() != numberOfSeats) {
                        View.displayError("Please select the correct number of seats!");
                        Form.pressAnyKeyToContinue();
                        navigation.refresh();
                    } else {
                        bookingController.selectSeats(booking.getId(), selectedSeats);
                        navigation.goTo(new BookingMenuView(navigation), accessLevel,
                                BookingMenuView.BookingMenuIntent.VERIFY_BOOKING, booking.getId().toString());
                    }
                    break;
            }
    }

    public enum CinemaMenuOption implements EnumerableMenuOption {

        CHOOSE_SEAT("Choose Seat(s)"),
        PROCEED_WITH_BOOKING("Proceed With Booking");

        private String description;
        CinemaMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
