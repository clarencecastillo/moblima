package view;

import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import model.cinema.Cineplex;
import model.movie.Movie;
import model.movie.MovieStatus;
import util.Utilities;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ShowtimeListView extends ListView {

    private Date dateFilter;
    private ArrayList<Cineplex> cineplexes;

    private CineplexShowtimeListIntent intent;
    private CineplexController cineplexController;
    private MovieController movieController;
    private ShowtimeController showtimeController;

    public ShowtimeListView(Navigation navigation) {
        super(navigation);
        this.cineplexController = CineplexController.getInstance();
        this.movieController = MovieController.getInstance();
        this.showtimeController = ShowtimeController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        this.intent = (CineplexShowtimeListIntent) intent;
        setTitle("Movie Showtimes");
        switch (this.intent) {
            case ADMIN:
                setMenuItems(CineplexShowtimeMenuOption.values());
                break;
            case PUBLIC:
                setMenuItems(CineplexShowtimeMenuOption.CHOOSE_DAY);
                break;
        }
        cineplexes = new ArrayList<>();
        dateFilter = args.length == 1 ? Utilities.parseDate(args[0]) : new Date();
        cineplexes = cineplexController.getList();
        setContent("Displaying showtimes from all cineplex for " + (args.length == 1 ?
                Utilities.toFormat(dateFilter, "EEEEE, dd MMMMM YYYY") : "today") + ".");
        addBackOption();
    }

    @Override
    public void onEnter() {

        setViewItems(cineplexes.stream().map(cineplex ->
                new ViewItem(new CineplexShowtimeView(cineplex, dateFilter, MovieStatus.NOW_SHOWING),
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
//                        Form.getOption("Date", )
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Go to showtime View view");
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
