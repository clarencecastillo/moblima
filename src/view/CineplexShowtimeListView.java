package view;

import config.BookingConfig;
import exception.NavigationRejectedException;
import manager.CineplexController;
import manager.MovieController;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;
import view.ui.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CineplexShowtimeListView extends ListView {

    private Date dateFilter;
    private Movie movieFilter;
    private ArrayList<Cineplex> cineplexes;

    private AccessLevel accessLevel;
    private CineplexController cineplexController;
    private MovieController movieController;

    public CineplexShowtimeListView(Navigation navigation) {
        super(navigation);
        this.cineplexController = CineplexController.getInstance();
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        // args[0] - Movie UUID
        // args[1] - Date

        this.accessLevel = accessLevel;
        switch (accessLevel) {

            case ADMINISTRATOR:
                setMenuItems(CineplexShowtimeMenuOption.values());
                break;
            case PUBLIC:
                setMenuItems(CineplexShowtimeMenuOption.CHOOSE_DAY);
                break;
        }

        if (args.length >= 1 && args[0] != null) {
            movieFilter = movieController.findById(UUID.fromString(args[0]));
            if (movieFilter == null) {
                View.displayError("Movie not found!!");
                Form.pressAnyKeyToContinue();
                throw new NavigationRejectedException();
            }
        }

        cineplexes = new ArrayList<>();
        dateFilter = args.length == 2 && args[1] != null ? Utilities.parseDate(args[1]) : new Date();
        cineplexes = cineplexController.getList();
        setTitle("Movie Showtimes");
        setContent("Movie: " + (movieFilter == null ? "All Movies" : new MovieView(movieFilter).getTitle()),
                "Cineplex: All Cineplexes",
                "Date: " + Utilities.toFormat(dateFilter, DATE_DISPLAY_FORMAT));
        addBackOption();
    }

    @Override
    public void onEnter() {

        // Get cineplex movies and generate view items
        setViewItems(cineplexes.stream().map(cineplex ->
                new ViewItem(movieFilter == null ?
                        new CineplexShowtimeView(cineplex, MovieStatus.NOW_SHOWING, dateFilter) :
                        new CineplexShowtimeView(cineplex, movieFilter, dateFilter),
                        cineplex.getId().toString())).toArray(ViewItem[]::new));

        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                CineplexShowtimeMenuOption userChoice = CineplexShowtimeMenuOption.valueOf(userInput);
                switch (userChoice) {
                    case ADD_SHOWTIME:
                        System.out.println("Go to Showtime View CREATE_MOVIE!");
                        break;
                    case CHOOSE_DAY:
                        View.displayInformation("Please select date");
                        Date today = new Date();

                        // Get user date selection from today to number of days before booking
                        String dateChoice = Form.getOption("Date", Utilities.getDaysBetweenDates(today,
                                Utilities.getDateAfter(today, Calendar.DAY_OF_YEAR,
                                        BookingConfig.getDaysBeforeOpenBooking())).stream()
                                .filter(date -> Utilities.getDateWithTime(date, 0, 0)
                                        .compareTo(Utilities.getDateWithTime(dateFilter, 0, 0)) != 0)
                                .map(date ->
                                        new GenericMenuOption(Utilities.toFormat(date, DATE_DISPLAY_FORMAT),
                                                Utilities.toFormat(date))).toArray(GenericMenuOption[]::new));
                        navigation.reload(accessLevel, movieFilter == null ? null :
                                movieFilter.getId().toString(), dateChoice);
                        break;
                }
            } catch (IllegalArgumentException e) {
                if (movieFilter != null) {
                    Cineplex cineplex = cineplexController.findById(UUID.fromString(userInput));
                    navigation.goTo(new ShowtimeListView(navigation), cineplex.getId().toString(),
                            movieFilter.getId().toString(), Utilities.toFormat(dateFilter));
                } else
                    navigation.goTo(new MovieShowtimeListView(navigation), userInput, Utilities.toFormat(dateFilter));
            }
    }

    public enum CineplexShowtimeMenuOption implements EnumerableMenuOption {

        CHOOSE_DAY("Choose Another Date"),
        ADD_SHOWTIME("Add Showtime");

        private String description;

        CineplexShowtimeMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
