package view;

import config.BookingConfig;
import exception.NavigationRejectedException;
import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;
import view.ui.*;

import javax.rmi.CORBA.Util;
import java.util.*;
import java.util.stream.Stream;

public class CineplexShowtimeListView extends ListView {

    private Date dateFilter;
    private Movie movieFilter;
    private ArrayList<Cineplex> cineplexes;

    private CineplexShowtimeListIntent intent;
    private CineplexController cineplexController;
    private MovieController movieController;

    public CineplexShowtimeListView(Navigation navigation) {
        super(navigation);
        this.cineplexController = CineplexController.getInstance();
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        this.intent = (CineplexShowtimeListIntent) intent;

        switch (this.intent) {
            case ADMIN:
                setMenuItems(CineplexShowtimeMenuOption.values());
                break;
            case PUBLIC:
                setMenuItems(CineplexShowtimeMenuOption.CHOOSE_DAY);
                break;
        }

        if (args.length == 2) {
            movieFilter = movieController.findById(UUID.fromString(args[1]));
            if (movieFilter == null) {
                View.displayError("Movie not found!!");
                Form.pressAnyKeyToContinue();
                throw new NavigationRejectedException();
            }
        }

        cineplexes = new ArrayList<>();
        dateFilter = args.length >= 1 && args[0] != null ? Utilities.parseDate(args[0]) : new Date();
        cineplexes = cineplexController.getList();
        setTitle("Movie Showtimes: " + (movieFilter == null ? "All Movies" : new MovieView(movieFilter).getTitle()));
        setContent("Displaying showtimes from all cineplex for " + (args.length >= 1 && args[0] != null ?
                Utilities.toFormat(dateFilter, DATE_DISPLAY_FORMAT) : "today") + ".");
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
                        System.out.println("Go to Showtime View CREATE!");
                        break;
                    case CHOOSE_DAY:
                        View.displayInformation("Please select date");
                        Date today = new Date();

                        // Get user date selection from today to number of days before booking
                        String dateChoice = Form.getOption("Date", Utilities.getDaysBetweenDates(today,
                                Utilities.getDateAfter(today, Calendar.DAY_OF_YEAR,
                                        BookingConfig.getMinDaysBeforeOpenBooking())).stream()
                                .filter(date -> Utilities.getDateWithTime(date, 0, 0)
                                        .compareTo(Utilities.getDateWithTime(dateFilter, 0, 0)) != 0)
                                .map(date ->
                                        new GenericMenuOption(Utilities.toFormat(date, DATE_DISPLAY_FORMAT),
                                                Utilities.toFormat(date))).toArray(GenericMenuOption[]::new));
                        navigation.reload(intent, dateChoice);
                        break;
                }
            } catch (IllegalArgumentException e) {
                if (movieFilter != null) {
                    Cineplex cineplex = cineplexController.findById(UUID.fromString(userInput));
                    navigation.goTo(new ShowtimeListView(navigation), cineplex.getId().toString(),
                            movieFilter.getId().toString(), Utilities.toFormat(dateFilter));
                } else
                    navigation.goTo(new CineplexMovieListView(navigation), userInput, Utilities.toFormat(dateFilter));
            }
    }

    public enum CineplexShowtimeListIntent implements NavigationIntent {
        ADMIN,
        PUBLIC
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
