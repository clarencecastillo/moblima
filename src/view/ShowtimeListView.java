package view;

import config.BookingConfig;
import exception.NavigationRejectedException;
import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import model.booking.Showtime;
import model.booking.ShowtimeStatus;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;
import view.ui.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class ShowtimeListView extends ListView {

    private Cineplex cineplexFilter;
    private Movie movieFilter;
    private Date dateFilter;
    private ShowtimeStatus showtimeStatusFilter;

    private List<Cineplex> cineplexes;
    private List<Movie> movies;
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

        // args[0] - Cineplex
        // args[1] - Movie
        // args[2] - Date

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

        if (args.length >= 1 && args[0] != null) {
            cineplexFilter = cineplexController.findById(UUID.fromString(args[0]));
            if (cineplexFilter == null) {
                View.displayError("Cineplex not found!");
                Form.pressAnyKeyToContinue();
                throw new NavigationRejectedException();
            }
        } else
            cineplexes = cineplexController.getList();

        if (args.length >= 2 && args[1] != null) {
            System.out.println(args[1]);
            movieFilter = movieController.findById(UUID.fromString(args[1]));
            if (movieFilter == null) {
                View.displayError("Movie not found!");
                Form.pressAnyKeyToContinue();
                throw new NavigationRejectedException();
            }

        } else if (cineplexFilter != null)
            movies = movieController.findByCineplex(cineplexFilter).stream().filter(movie ->
                    movie.getStatus() == MovieStatus.NOW_SHOWING).collect(Collectors.toList());
        else
            movies = movieController.getList();

        if (args.length == 3 && args[2] != null) {
            try {
                dateFilter = Utilities.parseDateIgnoreError(args[2]);
            } catch (ParseException e) {
                View.displayError("Invalid date!");
                Form.pressAnyKeyToContinue();
                throw new NavigationRejectedException();
            }
        } else
            dateFilter = new Date();

        setTitle("Movie Showtimes");
        addBackOption();
    }

    @Override
    public void onEnter() {

        setContent("Movie: " + (movieFilter == null ? "All Movies" : new MovieView(movieFilter).getTitle()),
                "Cineplex: " + (cineplexFilter == null ? "All Cineplexes" : cineplexFilter.getName()),
                "Date: " + Utilities.toFormat(dateFilter, DATE_DISPLAY_FORMAT));

        if (cineplexFilter == null) {
            setViewItems(cineplexes.stream().map(cineplex ->
                    new ViewItem(movieFilter == null ?
                            new ShowtimeView(cineplex, MovieStatus.NOW_SHOWING, dateFilter) :
                            new ShowtimeView(cineplex, movieFilter, dateFilter),
                            cineplex.getId().toString())).toArray(ViewItem[]::new));
        } else if (movieFilter == null) {
            setViewItems(movies.stream().map(movie ->
                    new ViewItem(new ShowtimeView(cineplexFilter, movie, dateFilter),
                    movie.getId().toString())).toArray(ViewItem[]::new));
        } else {

            showtimes = showtimeController.findByCineplexAndMovie(cineplexFilter, movieFilter).stream().filter(showtime ->
                    Utilities.getStartOfDate(showtime.getStartTime())
                            .compareTo(Utilities.getStartOfDate(dateFilter)) == 0).collect(Collectors.toList());

            if (showtimeStatusFilter != null)
                showtimes = showtimes.stream().filter(showtime ->
                        showtime.getStatus() == showtimeStatusFilter).collect(Collectors.toList());

            setViewItems(showtimes.stream().map(showtime ->
                    new ViewItem(new ShowtimeView(showtime), showtime.getId().toString())).toArray(ViewItem[]::new));
        }


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
                                        BookingConfig.getDaysBeforeOpenBooking())).stream()
                                .filter(date -> Utilities.getDateWithTime(date, 0, 0)
                                        .compareTo(Utilities.getDateWithTime(dateFilter, 0, 0)) != 0)
                                .map(date -> new GenericMenuOption(Utilities.toFormat(date, DATE_DISPLAY_FORMAT),
                                        Utilities.toFormat(date))).toArray(GenericMenuOption[]::new));
                        dateFilter = Utilities.parseDate(dateChoice);
                        navigation.refresh();
                        break;
                }
            } catch (IllegalArgumentException e) {
                if (cineplexFilter == null) {
                    navigation.goTo(new ShowtimeListView(navigation), accessLevel,
                            userInput,
                            movieFilter == null ? null : movieFilter.getId().toString(),
                            Utilities.toFormat(dateFilter));
                } else if (movieFilter == null) {
                    navigation.goTo(new ShowtimeListView(navigation), accessLevel,
                            cineplexFilter == null ? null : cineplexFilter.getId().toString(),
                            userInput,
                            Utilities.toFormat(dateFilter));
                } else
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
