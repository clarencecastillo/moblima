package controller;

import java.util.ArrayList;
import java.util.Arrays;
import manager.MovieManager;
import model.movie.Movie;
import model.movie.MoviePerson;
import view.ListMenu;
import view.ListMenuItem;

public class MovieListController extends Controller {

    private static MovieListController instance = new MovieListController();

    private ListMenu movieListMenu;

    private MovieManager movieManager;

    private MovieListController() {
        movieManager = MovieManager.getInstance();
    }

    public static MovieListController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        movieListMenu = new ListMenu();
    }

    @Override
    public void onLoad(String[] arguments) {

        MovieListIntent intent = MovieListIntent.valueOf(arguments[0]);
        ArrayList<Movie> movies = new ArrayList<>();

        switch (intent) {
            case SEARCH:

                movieListMenu.setTitle("Search Movies");
                movieListMenu.setContent(new String[] {
                    "Please enter search terms. Keywords may include movie "
                    + "title, director, and actors."
                });

                movieListMenu.displayHeader();
                movieListMenu.display();

                String searchKeyword = movieListMenu.getString("Enter keywords");
                movies.addAll(Arrays.asList(movieManager.findByKeyword(searchKeyword)));

                movieListMenu.setTitle("Search Results");
                movieListMenu.setContent(new String[] {
                    "Your search '" + searchKeyword + "' yielded "
                    + movies.size() + " movie items."
                });
                break;
        }

        navigation.clearScreen();
        movieListMenu.displayHeader();
        movieListMenu.display();

        ArrayList<ListMenuItem> items = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            ListMenuItem movieView = new ListMenuItem(i + 1);
            movieView.setTitle(String.format("%s [%s] %s", movie.getTitle(),
                                             movie.getType(), movie.getRating()));
            MoviePerson[] movieActors = movie.getActors();
            String[] actorNames = new String[movieActors.length];
            for (int a = 0; a < movieActors.length; a++)
                actorNames[a] = movieActors[a].getFullName();
            String directorName = movie.getDirector().getFullName();
            String movieScore = movie.getOverallReviewRating() == -1 ? "NA" :
                                String.format("%.1f/5.0", movie.getOverallReviewRating());
            movieView.setContent(new String[] {
                "Director: " + directorName,
                "Actors: " + String.join(",", actorNames),
                "Score: " + movieScore,
                "Sypnosis",
                movie.getSypnosis()
            });
            items.add(movieView);
        }

        movieListMenu.setMenuItems(items.toArray(new ListMenuItem[items.size()]));
        movieListMenu.displayMenuItemsWithBack();
        movieListMenu.getChoice();
    }

    public enum MovieListIntent {
        SEARCH
    }
}
