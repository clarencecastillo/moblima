package view;

import manager.CineplexController;
import manager.MovieController;
import manager.ShowtimeController;
import model.booking.Showtime;
import model.cinema.Cineplex;
import model.movie.Movie;
import util.Utilities;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class ShowtimeListView extends ListView {

    private ShowtimeListIntent intent;
    private ShowtimeController showtimeController;
    private MovieController movieController;
    private CineplexController cineplexController;

    private Date dateFilter;
    private ArrayList<Cineplex> cineplexes;
    private ArrayList<Showtime> showtimes;
    private Movie movie;

    public ShowtimeListView(Navigation navigation) {
        super(navigation);
        this.showtimeController = ShowtimeController.getInstance();
        this.movieController = MovieController.getInstance();
        this.cineplexController = CineplexController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        this.intent = (ShowtimeListIntent) intent;
        setTitle("Movie Showtimes");
        showtimes = new ArrayList<>();
        switch (this.intent) {
            case CINEPLEX:
                dateFilter = Utilities.parseDate(args[0]);
                cineplexes = cineplexController.getList();
                break;
            case CINEPLEX_MOVIE:
                movie = movieController.findById(UUID.fromString(args[0]));
            case ADMIN:
                setMenuItems(ShowtimeListMenuOption.ADD_SHOWTIME);
                break;
        }
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        switch (this.intent) {
            case CINEPLEX:
                for (Cineplex cineplex: cineplexes)
                    viewItems.add(new ViewItem(new CineplexShowtimesView(cineplex, dateFilter),
                            cineplex.getId().toString()));
                break;
            case CINEPLEX_MOVIE:
                showtimes.addAll(Arrays.asList(movie.getShowtimes()));
                setContent("Displaying " + showtimes.size() + " showtime item(s) for " + new MovieView(movie).getTitle());
                break;
            case ADMIN:
                showtimes.addAll(showtimeController.getList());
                setContent("Displaying " + showtimes.size() + " showtime item(s).");
                break;
//            case PUBLIC:
//                showtimes.addAll(showtimeController.findByStatus(ShowtimeStatus.OPEN_BOOKING));
//                setContent("Displaying " + showtimes.size() + " showtime item(s).");
//                break;
        }
        setViewItems(viewItems.toArray(new ViewItem[viewItems.size()]));

        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                ShowtimeListMenuOption userChoice = ShowtimeListMenuOption.valueOf(userInput);
                switch (userChoice) {
                    case ADD_SHOWTIME:
                        System.out.println("Create showtime!");
//                        navigation.goTo(new MovieMenuView(navigation), ShowtimeListIntent.CREATE);
                        break;
                }
            } catch (IllegalArgumentException e) {
//                navigation.goTo(new MovieMenuView(navigation),
//                        this.intent == ShowtimeListIntent.ADMIN ?
//                                ShowtimeMenuIntent.MANAGE : ShowtimeMenuIntent.VIEW, userInput);
            }

    }

    public enum ShowtimeListIntent implements NavigationIntent {
        ADMIN,
        CINEPLEX,
        CINEPLEX_MOVIE,
        CINEPLEX_MOVIE_SHOWTIME
    }

    public enum ShowtimeListMenuOption implements EnumerableMenuOption {

        ADD_SHOWTIME("Add Showtime");

        private String description;
        ShowtimeListMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

}
