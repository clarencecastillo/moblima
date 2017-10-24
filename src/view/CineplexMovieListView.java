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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CineplexMovieListView extends ListView {

    private Date dateFilter;
    private Cineplex cineplex;
    private List<Movie> movies;

    private MovieController movieController;
    private CineplexController cineplexController;

    public CineplexMovieListView(Navigation navigation) {
        super(navigation);
        this.movieController = MovieController.getInstance();
        this.cineplexController = CineplexController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {

        cineplex = cineplexController.findById(UUID.fromString(args[0]));
        if (cineplex == null) {
            View.displayError("Cineplex not found!!");
            Form.pressAnyKeyToContinue();
            throw new NavigationRejectedException();
        }

        dateFilter = args.length == 2 ? Utilities.parseDate(args[1]) : new Date();
        movies = movieController.findByCineplex(cineplex).stream().filter(movie ->
                movie.getStatus() == MovieStatus.NOW_SHOWING).collect(Collectors.toList());

        setTitle(cineplex.getName() + " Movie Showtimes");
        setContent("Displaying showtimes for " + (args.length == 2 ?
                Utilities.toFormat(dateFilter, DATE_DISPLAY_FORMAT) : "today") + ".");
        setMenuItems(CineplexMovieMenuOption.values());
        addBackOption();
    }

    @Override
    public void onEnter() {
        // Get showtimes movies and generate view items
        setViewItems(movies.stream().map(movie -> new ViewItem(new MovieShowtimeView(movie, cineplex, dateFilter),
                        cineplex.getId().toString())).toArray(ViewItem[]::new));

        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                CineplexMovieMenuOption userChoice = CineplexMovieMenuOption.valueOf(userInput);
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
                                    .map(date ->
                                            new GenericMenuOption(Utilities.toFormat(date, DATE_DISPLAY_FORMAT),
                                                    Utilities.toFormat(date))).toArray(GenericMenuOption[]::new));
                            navigation.reload(cineplex.getId().toString(), dateChoice);
                            break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Go to showtime View view");
            }
    }

public enum CineplexMovieMenuOption implements EnumerableMenuOption {

    CHOOSE_DAY("Choose Another Date");

    private String description;
    CineplexMovieMenuOption(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
}
