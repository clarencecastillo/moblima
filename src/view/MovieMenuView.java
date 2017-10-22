package view;

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
                             MovieMenuOption.SEE_REVIEWS,
                             MovieMenuOption.WRITE_REVIEW);
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

        MovieView movieView = new MovieView(movie);
        setTitle(movieView.getTitle());
        setContent(movieView.getContent());
        addBackOption();
    }

    @Override
    public void onEnter() {
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
                case WRITE_REVIEW:
                    break;
                case UPDATE:
                    break;
                case REMOVE:
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
        WRITE_REVIEW("Write Review"),
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
