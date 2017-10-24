package view;

import config.BookingConfig;
import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import model.booking.Showtime;
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
    private List<Showtime> showtimes;

    private CineplexController cineplexController;
    private MovieController movieController;
    private ShowtimeController showtimeController;

    public ShowtimeListView(Navigation navigation) {
        super(navigation);
        cineplexController = CineplexController.getInstance();
        movieController = MovieController.getInstance();
        showtimeController = ShowtimeController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        cineplexFilter = cineplexController.findById(UUID.fromString(args[0]));
        movieFilter = movieController.findById(UUID.fromString(args[1]));
        dateFilter = Utilities.parseDate(args[2]);

        showtimes = showtimeController.findByCineplexAndMovie(cineplexFilter, movieFilter).stream().filter(showtime ->
                Utilities.getStartOfDate(showtime.getStartTime())
                        .compareTo(Utilities.getStartOfDate(dateFilter)) == 0).collect(Collectors.toList());

        setTitle("Showtimes for " + new MovieView(movieFilter).getTitle() + " at " + cineplexFilter.getName());
        setContent("Displaying " + showtimes.size() +  " showtimes for "
                + Utilities.toFormat(dateFilter, DATE_DISPLAY_FORMAT) + ".");
        setViewItems(showtimes.stream().map(showtime ->
                new ViewItem(new ShowtimeView(showtime), showtime.getId().toString())).toArray(ViewItem[]::new));
        setMenuItems(ShowtimeMenuOption.values());
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
                        navigation.reload(cineplexFilter.getId().toString(),
                                movieFilter.getId().toString(), dateChoice);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Go to showtime View view");
            }

    }

    public enum ShowtimeMenuOption implements EnumerableMenuOption {

        CHOOSE_DAY("Choose Another Date");

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
