package view;

import controller.MovieController;
import exception.IllegalActionException;
import exception.UnauthorisedNavigationException;
import model.movie.*;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * This moblima.view displays the user interface for the user to select action in movie listings.
 *
 * @version 1.0
 * @since 2017-10-30
 */
public class MovieMenuView extends MenuView {

    private AccessLevel accessLevel;
    private MovieMenuIntent intent;
    private Movie movie;

    private MovieController movieController;

    public MovieMenuView(Navigation navigation) {
        super(navigation);
        this.movieController = MovieController.getInstance();

    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        this.intent = (MovieMenuIntent) intent;
        switch (this.intent) {
            case VIEW_MOVIE:
                movie = movieController.findById(UUID.fromString(args[0]));
                break;
            case CREATE_MOVIE:
                if (accessLevel != AccessLevel.ADMINISTRATOR)
                    throw new UnauthorisedNavigationException();

                View.displayInformation("Please enter movie details.");
                String title = Form.getString("Title");
                String sypnosis = Form.getString("Sypnosis");
                MoviePerson director = new MoviePerson(Form.getString("Director"));
                int numberOfActors = Form.getIntWithMin("Number of Actors", 0);
                MoviePerson[] actors = new MoviePerson[numberOfActors];
                for (int i = 0; i < numberOfActors; i++)
                    actors[i] = new MoviePerson(Form.getString("Actor " + (i + 1) + " Name"));
                MovieType type = MovieType.valueOf(Form.getOption("Movie Type", MovieType.values()));
                MovieStatus status = MovieStatus.valueOf(Form.getOption("Movie Status", MovieStatus.values()));
                MovieRating rating = MovieRating.valueOf(Form.getOption("Movie Rating", MovieRating.values()));
                int runtime = Form.getIntWithMin("Runtime Minutes", 0);
                movie = movieController.createMovie(title, sypnosis, director, actors, type, status, rating, runtime);
                setMenuItems(MovieMenuOption.values());
                View.displaySuccess("Successfully created movie!");
                Form.pressAnyKeyToContinue();
                break;
        }

        this.accessLevel = accessLevel;
        switch (accessLevel) {
            case ADMINISTRATOR:
                setMenuItems(MovieMenuOption.values());
                break;
            case PUBLIC:
                ArrayList<MovieMenuOption> menuOptions = new ArrayList<>();
                menuOptions.add(MovieMenuOption.SEE_REVIEWS);
                if (Arrays.asList(MovieStatus.NOW_SHOWING, MovieStatus.PREVIEW).contains(movie.getStatus()))
                    menuOptions.add(MovieMenuOption.VIEW_SHOWTIMES);
                setMenuItems(menuOptions.toArray(new MovieMenuOption[menuOptions.size()]));
                break;
        }

        addBackOption();
    }

    @Override
    public void onEnter() {

        MovieView movieView = new MovieView(movie, true, accessLevel == AccessLevel.ADMINISTRATOR);
        setTitle(movieView.getTitle());
        setContent(movieView.getContent());
        content.add(0, "Status: " + movie.getStatus().toString());
        display();

        if (accessLevel == AccessLevel.ADMINISTRATOR)
            View.displayInformation("Gross: " + String.format("$%.2f", movie.getGrossSales()) +
                    "\nWeekend Gross: " + String.format("$%.2f", movie.getWeekendGrossSales()));

        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (MovieMenuOption.valueOf(userChoice)) {
                case VIEW_SHOWTIMES:
                    navigation.goTo(new ShowtimeListView(navigation), accessLevel,
                            ShowtimeListView.ShowtimeListIntent.VIEW_SHOWTIMES,
                            null, movie.getId().toString());
                    break;
                case SEE_REVIEWS:
                    navigation.goTo(new MovieReviewListView(navigation), accessLevel, movie.getId().toString());
                    break;
                case CHANGE_STATUS:
                    View.displayInformation("Please enter new status.");
                    MovieStatus status = MovieStatus.valueOf(Form.getOption("Movie Status",
                            MovieStatus.PREVIEW, MovieStatus.NOW_SHOWING, MovieStatus.END_OF_SHOWING));
                    try {
                        movieController.changeMovieStatus(movie.getId(), status);
                        View.displaySuccess("Successfully updated movie!");
                    } catch (IllegalActionException e) {
                        View.displayError(e.getMessage());
                    }
                    Form.pressAnyKeyToContinue();
                    navigation.reload(accessLevel, MovieMenuIntent.VIEW_MOVIE, movie.getId().toString());
                    break;
                case UPDATE:
                    View.displayInformation("Please enter updated movie details.");
                    String title = Form.getString("Title");
                    String sypnosis = Form.getString("Sypnosis");
                    MoviePerson director = new MoviePerson(Form.getString("Director"));
                    int numberOfActors = Form.getIntWithMin("Number of Actors", 0);
                    MoviePerson[] actors = new MoviePerson[numberOfActors];
                    for (int i = 0; i < numberOfActors; i++)
                        actors[i] = new MoviePerson(Form.getString("Actor " + (i + 1) + " Name"));
                    MovieRating rating = MovieRating.valueOf(Form.getOption("Movie Rating",
                            MovieRating.values()));
                    int runtime = Form.getIntWithMin("Runtime Minutes", 0);
                    movieController.changeMovieDetails(movie.getId(), title, sypnosis,
                            director, actors, rating, runtime);
                    View.displaySuccess("Successfully updated movie!");
                    Form.pressAnyKeyToContinue();
                    navigation.reload(accessLevel, MovieMenuIntent.VIEW_MOVIE, movie.getId().toString());
                    break;
            }
    }

    public enum MovieMenuIntent implements Intent {
        VIEW_MOVIE,
        CREATE_MOVIE
    }

    private enum MovieMenuOption implements EnumerableMenuOption {

        VIEW_SHOWTIMES("View Showtimes"),
        SEE_REVIEWS("See Reviews"),
        CHANGE_STATUS("Change Status"),
        UPDATE("Update");

        private String description;

        MovieMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
