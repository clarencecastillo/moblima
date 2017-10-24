package view;

import config.BookingConfig;
import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.cinema.Cineplex;
import model.movie.Movie;
import util.Utilities;
import view.ui.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShowtimeListView extends ListView {

    private Cineplex cineplexFilter;
    private Movie movieFilter;
    private Date dateFilter;
    private ShowtimeStatus showtimeStatusFilter;
    private List<Showtime> showtimes;

    private CineplexController cineplexController;
    private MovieController movieController;
    private ShowtimeController showtimeController;

    private AccessLevel accessLevel;

    public ShowtimeListView(Navigation navigation) {
        super(navigation);
        cineplexController = CineplexController.getInstance();
        movieController = MovieController.getInstance();
        showtimeController = ShowtimeController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                setMenuItems(ShowtimeMenuOption.values());
                showtimeStatusFilter = ShowtimeStatus.OPEN_BOOKING;
                break;
            case PUBLIC:
                setMenuItems(ShowtimeMenuOption.CHOOSE_DAY);
                break;
        }

        cineplexFilter = cineplexController.findById(UUID.fromString(args[0]));
        movieFilter = movieController.findById(UUID.fromString(args[1]));
        dateFilter = Utilities.parseDate(args[2]);

        showtimes = showtimeController.findByCineplexAndMovie(cineplexFilter, movieFilter).stream().filter(showtime ->
                Utilities.getStartOfDate(showtime.getStartTime())
                        .compareTo(Utilities.getStartOfDate(dateFilter)) == 0).collect(Collectors.toList());

        if (showtimeStatusFilter != null)
            showtimes = showtimes.stream().filter(showtime ->
                    showtime.getStatus() == showtimeStatusFilter).collect(Collectors.toList());

        setTitle("Movie Showtimes");
        setContent("Movie: " + new MovieView(movieFilter).getTitle(),
                "Cineplex: " + cineplexFilter.getName(),
                "Date: " + Utilities.toFormat(dateFilter, DATE_DISPLAY_FORMAT));
        setViewItems(showtimes.stream().map(showtime ->
                new ViewItem(new ShowtimeView(showtime), showtime.getId().toString())).toArray(ViewItem[]::new));
        addBackOption();
    }

    @Override
    public void onEnter() {
        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                ShowtimeMenuOption userChoice = ShowtimeMenuOption.valueOf(userInput);
                switch (userChoice) {
                    case CHOOSE_DAY:
                        View.displayInformation("Please select date");
                        Date today = new Date();
                        // Get user date selection from today to number of days before booking
                        String dateChoice = Form.getOption("Date", Utilities.getDaysBetweenDates(today,
                                Utilities.getDateAfter(today, Calendar.DAY_OF_YEAR,
                                        BookingConfig.getMinDaysBeforeOpenBooking())).stream()
                                .filter(date -> Utilities.getDateWithTime(date, 0, 0)
                                        .compareTo(Utilities.getDateWithTime(dateFilter, 0, 0)) != 0)
                                .map(date -> new GenericMenuOption(Utilities.toFormat(date, DATE_DISPLAY_FORMAT),
                                                Utilities.toFormat(date))).toArray(GenericMenuOption[]::new));
                        navigation.reload(accessLevel, cineplexFilter.getId().toString(),
                                movieFilter.getId().toString(), dateChoice);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Go to showtime View view");
            }

    }

    public enum ShowtimeMenuOption implements EnumerableMenuOption {

        CHOOSE_DAY("Choose Another Date"),
        ADD_SHOWTIME("Add Showtime");

        private String description;
        ShowtimeMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
