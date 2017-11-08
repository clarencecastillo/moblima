package view;

import controller.MovieController;
import model.movie.Movie;
import model.movie.MovieStatus;
import view.MovieMenuView.MovieMenuIntent;
import view.ui.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This moblima.view displays the user interface for the user moblima.view movie listings.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class MovieListView extends ListView {

    private static final String SCORE = "score";
    private static final String SALES = "gross sales";

    private MovieListIntent intent;
    private List<Movie> movies;
    private String searchKeyword;
    private boolean withGrouping;
    private MovieController movieController;
    private AccessLevel accessLevel;
    private String rankBy;

    public MovieListView(Navigation navigation) {
        super(navigation);
        this.movies = new ArrayList<>();
        this.movieController = MovieController.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {
        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                setMenuItems(MovieListOption.ADD_MOVIE, MovieListOption.SEARCH_MOVIES);
                break;
            case PUBLIC:
                setMenuItems(MovieListOption.SEARCH_MOVIES);
                break;
        }

        this.intent = (MovieListIntent) intent;
        setTitle("Movie Listings");
        switch (this.intent) {
            case SEARCH_MOVIES:
                View.displayInformation("Please enter search terms. Keywords may include movie "
                        + "title, director, and actors.");
                searchKeyword = Form.getString("Search");
                break;
            case RANK_MOVIES:
                View.displayInformation("Would you like to see ranking by review score or sales?");
                this.rankBy = Form.getOption("Rank By",
                        new GenericMenuOption("Review Score", SCORE),
                        new GenericMenuOption("Sales", SALES));
                break;
        }
        addBackOption();
    }

    @Override
    public void onEnter() {

        movies = new ArrayList<>();
        switch (this.intent) {
            case SEARCH_MOVIES:
                movies.addAll(movieController.findByKeyword(searchKeyword));
                if (accessLevel == AccessLevel.PUBLIC)
                    movies = movies.stream().filter(movie ->
                            movie.getStatus() != MovieStatus.END_OF_SHOWING).collect(Collectors.toList());
                setContent("Your search for '" + searchKeyword + "' yielded "
                        + movies.size() + " movie item(s).");
                break;
            case VIEW_MOVIES:
                withGrouping = true;
                movies.addAll(movieController.getList());
                if (accessLevel == AccessLevel.PUBLIC)
                    movies = movies.stream().filter(movie ->
                            movie.getStatus() != MovieStatus.END_OF_SHOWING).collect(Collectors.toList());
                setContent("Displaying " + movies.size() + " movie item(s).");
                break;
            case RANK_MOVIES:
                movies.addAll(movieController.getList());
                Collections.sort(movies, Comparator.comparingDouble(rankBy.equals(SCORE) ?
                        Movie::getOverallReviewRating :
                        Movie::getGrossSales)
                        .reversed());
                if (movies.size() > 5)
                    movies = movies.subList(0, 5);
                setContent("Displaying top " + movies.size() + " movie item(s) by " + rankBy + ".");
                break;
        }

        setViewItems(movies.stream().map(
                movie -> new ViewItem(new MovieView(movie, rankBy == null || rankBy.equals(SCORE),
                        rankBy == null ? accessLevel == AccessLevel.ADMINISTRATOR : rankBy.equals(SALES)),
                        movie.getId().toString(), 0, withGrouping ? movie.getStatus().toString() : null))
                .collect(Collectors.toList()));

        display();
        String userInput = getChoice();
        if (userInput.equals(BACK))
            navigation.goBack();
        else
            try {
                MovieListOption userChoice = MovieListOption.valueOf(userInput);
                switch (userChoice) {
                    case ADD_MOVIE:
                        navigation.goTo(new MovieMenuView(navigation), accessLevel, MovieMenuIntent.CREATE_MOVIE);
                        break;
                    case SEARCH_MOVIES:
                        navigation.reload(accessLevel, MovieListIntent.SEARCH_MOVIES);
                }
            } catch (IllegalArgumentException e) {
                navigation.goTo(new MovieMenuView(navigation), accessLevel, MovieMenuIntent.VIEW_MOVIE, userInput);
            }
    }

    public enum MovieListIntent implements Intent {
        VIEW_MOVIES,
        SEARCH_MOVIES,
        RANK_MOVIES
    }

    public enum MovieListOption implements EnumerableMenuOption {

        ADD_MOVIE("Add Movie"),
        SEARCH_MOVIES("Search Movies");

        private String description;

        MovieListOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
