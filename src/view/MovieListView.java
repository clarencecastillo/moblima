package view;

import manager.MovieController;
import model.movie.Movie;
import model.movie.MovieStatus;
import view.MovieMenuView.MovieMenuIntent;
import view.ui.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MovieListView extends ListView {

    private MovieListIntent intent;
    private List<Movie> movies;
    private String searchKeyword;

    private MovieController movieController;
    private AccessLevel accessLevel;

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
                setMenuItems(MovieListOption.ADD_MOVIE);
                break;
            case PUBLIC:
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
            case VIEW_RANKING:
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
                movies.addAll(movieController.getList());
                if (accessLevel == AccessLevel.PUBLIC)
                    movies = movies.stream().filter(movie ->
                            movie.getStatus() != MovieStatus.END_OF_SHOWING).collect(Collectors.toList());
                setContent("Displaying " + movies.size() + " movie item(s).");
                break;
            case VIEW_RANKING:
                movies.addAll(movieController.getList());
                Collections.sort(movies);
                if (movies.size() > 5)
                    movies = movies.subList(0, 5);
                setContent("Displaying top " + movies.size() + " movie item(s) by score.");
                break;
        }

        setViewItems(movies.stream().map(
                movie -> new ViewItem(new MovieView(movie),
                        movie.getId().toString())).collect(Collectors.toList()));

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
                }
            } catch (IllegalArgumentException e) {
                navigation.goTo(new MovieMenuView(navigation), accessLevel, MovieMenuIntent.VIEW_MOVIE, userInput);
            }
    }

    public enum MovieListIntent implements Intent {
        VIEW_MOVIES,
        SEARCH_MOVIES,
        VIEW_RANKING
    }

    public enum MovieListOption implements EnumerableMenuOption {

        ADD_MOVIE("Add Movie");

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
