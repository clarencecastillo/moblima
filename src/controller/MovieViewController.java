package controller;

import java.util.Arrays;
import java.util.UUID;
import manager.MovieManager;
import model.movie.Movie;
import view.Describable;
import view.Menu;

public class MovieViewController extends Controller {

    private static MovieViewController instance = new MovieViewController();

    protected Menu movieViewMenu;

    private MovieManager movieManager;

    private MovieViewController() {
        movieManager = MovieManager.getInstance();
    }

    public static MovieViewController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        movieViewMenu = new Menu();
        movieViewMenu.setMenuItems(MovieViewMenuOption.values());
    }

    @Override
    public void onEnter(String[] arguments) {

        Movie movie = movieManager.findById(UUID.fromString(arguments[0]));
        movieViewMenu.setTitle(String.format("%s [%s] %s", movie.getTitle(),
                                             movie.getType(), movie.getRating()));
        movieViewMenu.displayHeader();

        movieViewMenu.setContent(new String[] {
            "Director: " + movie.getDirector(),
            "Actors: " + String.join(",", Arrays.stream(movie.getActors())
                                                .map(String::valueOf).toArray(String[]::new)),
            "Runtime: " + movie.getRuntimeMinutes(),
            "Score: " + (movie.getOverallReviewRating() == -1 ? "NA" :
                         String.format("%.1f/5.0", movie.getOverallReviewRating())),
            " ",
            "Sypnosis",
            "--------",
            movie.getSypnosis()
        });
        movieViewMenu.displayContent();

        movieViewMenu.displayItems();
        MovieViewMenuOption userChoice = MovieViewMenuOption.valueOf(movieViewMenu.getChoice());
        switch (userChoice) {
            case VIEW_SHOWTIMES:
                break;
            case SEE_REVIEWS:
                break;
            case WRITE_REVIEW:
                break;
            case GO_BACK:
                navigation.goBack();
                break;
        }
    }

    private enum MovieViewMenuOption implements Describable {

        VIEW_SHOWTIMES("View Showtimes"),
        SEE_REVIEWS("See Reviews"),
        WRITE_REVIEW("Write Review"),
        GO_BACK("Go Back");

        private String description;

        MovieViewMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}

