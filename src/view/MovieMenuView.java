package view;

import exception.IllegalMovieStatusTransitionException;
import manager.MovieController;
import model.movie.*;
import view.ui.*;

import java.util.UUID;

public class MovieMenuView extends MenuView {

    private Movie movie;

    private MovieController movieController;

    public MovieMenuView(Navigation navigation) {
        super(navigation);
        this.movieController = MovieController.getInstance();

    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        switch ((MovieMenuIntent) intent) {
            case MANAGE:
                movie = movieController.findById(UUID.fromString(args[0]));
                setMenuItems(MovieMenuOption.values());
                break;
            case VIEW:
                movie = movieController.findById(UUID.fromString(args[0]));
                setMenuItems(MovieMenuOption.VIEW_SHOWTIMES,
                             MovieMenuOption.SEE_REVIEWS);
                break;
            case CREATE:
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
        addBackOption();
    }

    @Override
    public void onEnter() {

        MovieView movieView = new MovieView(movie);
        setTitle(movieView.getTitle());
        setContent(movieView.getContent());

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (MovieMenuOption.valueOf(userChoice)) {
                case VIEW_SHOWTIMES:
                    break;
                case SEE_REVIEWS:
                    break;
                case CHANGE_STATUS:
                    View.displayInformation("Please enter new status.");
                    MovieStatus status = MovieStatus.valueOf(Form.getOption("Movie Status",
                            MovieStatus.PREVIEW, MovieStatus.NOW_SHOWING));
                    try {
                        movieController.changeMovieStatus(movie.getId(), status);
                        View.displaySuccess("Successfully updated movie!");
                    } catch (IllegalMovieStatusTransitionException e) {
                        View.displayError("Cannot change movie status to " + status + "!");
                    }
                    Form.pressAnyKeyToContinue();
                    navigation.reload();
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
                    navigation.reload();
                    break;
                case REMOVE:
                    try {
                        movieController.changeMovieStatus(movie.getId(), MovieStatus.END_OF_SHOWING);
                        View.displaySuccess("Successfully removed movie!");
                        Form.pressAnyKeyToContinue();
                        navigation.goBack();
                    } catch (IllegalMovieStatusTransitionException e) {
                        View.displayError("Cannot remove movie!");
                        Form.pressAnyKeyToContinue();
                        navigation.reload();
                    }
                    break;
            }
    }

    public enum MovieMenuIntent implements NavigationIntent {
        MANAGE,
        VIEW,
        CREATE
    }

    private enum MovieMenuOption implements EnumerableMenuOption {

        VIEW_SHOWTIMES("View Showtimes"),
        SEE_REVIEWS("See Reviews"),
        CHANGE_STATUS("Change Status"),
        UPDATE("Update"),
        REMOVE("Remove");

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
