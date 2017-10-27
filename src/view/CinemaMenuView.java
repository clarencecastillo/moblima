package view;

import exception.RejectedNavigationException;
import manager.BookingController;
import model.booking.Booking;
import model.booking.ShowtimeSeating;
import model.cinema.CinemaLayout;
import model.cinema.Seat;
import view.ui.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class CinemaMenuView extends MenuView {

    private Booking booking;
    private CinemaLayout cinemaLayout;
    private ShowtimeSeating showtimeSeating;
    private ArrayList<Seat> selectedSeats;
    private int numberOfSeats;

    private AccessLevel accessLevel;
    private BookingController bookingController;

    public CinemaMenuView(Navigation navigation) {
        super(navigation);
        this.bookingController = BookingController.getInstance();
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

        showtimeSeating = booking.getShowtime().getSeating();
        cinemaLayout = booking.getShowtime().getCinema().getLayout();
        selectedSeats = new ArrayList<>();
        numberOfSeats = booking.getTotalTicketsCount();

        setTitle("Seat Selection");
        setMenuItems(CinemaMenuOption.values());
        addBackOption();
    }

    @Override
    public void onEnter() {
        setContent(new CinemaView(showtimeSeating, cinemaLayout, selectedSeats).getContent());
        display();

        View.displayInformation("Seats to Choose: " + (numberOfSeats - selectedSeats.size()));

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
