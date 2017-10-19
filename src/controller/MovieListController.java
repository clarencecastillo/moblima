package controller;

import java.util.ArrayList;
import java.util.Arrays;
import manager.MovieManager;
import model.movie.Movie;
import model.movie.MoviePerson;
import view.Describable;
import view.Menu;
import view.ViewItem;

public class MovieListController extends Controller {

    private static MovieListController instance = new MovieListController();

    private Menu movieListMenu;

    private MovieManager movieManager;

    private MovieListController() {
        movieManager = MovieManager.getInstance();
    }

    public static MovieListController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        movieListMenu = new Menu();
        movieListMenu.setTitle("Movie Listings");
    }

    @Override
    public void onLoad(String[] arguments) {

        MovieListIntent intent = MovieListIntent.valueOf(arguments[0]);
        ArrayList<Movie> movies = new ArrayList<>();

        switch (intent) {
            case SEARCH:

                movieListMenu.setTitle("Search Movies");
                movieListMenu.setContent("Please enter search terms. Keywords may include movie "
                                         + "title, director, and actors."
                );

                movieListMenu.displayHeader();
                movieListMenu.displayContent();

                String searchKeyword = movieListMenu.getString("Enter keywords");
                movies.addAll(Arrays.asList(movieManager.findByKeyword(searchKeyword)));

                movieListMenu.setTitle("Search Results");
                movieListMenu.setContent("Your search '" + searchKeyword + "' yielded "
                                         + movies.size() + " movie items.");

                movieListMenu.setMenuItems(new MovieListMenuOption[] {
                    MovieListMenuOption.GO_BACK
                });
                break;
            case ADMIN:

                movies.addAll(movieManager.getList());
                movieListMenu.setMenuItems(new MovieListMenuOption[] {
                    MovieListMenuOption.ADD_MOVIE,
                    MovieListMenuOption.GO_BACK
                });
                break;
        }

        navigation.clearScreen();
        movieListMenu.displayHeader();
        movieListMenu.displayContent();

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            ViewItem movieView = new ViewItem(movie.getId().toString());
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
                "Runtime: " + movie.getRuntimeMinutes(),
                "Score: " + movieScore,
                "Sypnosis",
                movie.getSypnosis()
            });
            viewItems.add(movieView);
        }

        movieListMenu.setViewItems(viewItems.toArray(new ViewItem[viewItems.size()]));
        movieListMenu.displayItems();

        String userInput = movieListMenu.getChoice();
        try {
            MovieListMenuOption userChoice = MovieListMenuOption.valueOf(userInput);
            switch (userChoice) {
                case ADD_MOVIE:
                    System.out.println("Add movie!");
                    break;
                case GO_BACK:
                    navigation.goBack();
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Selected movie with ID " + userInput);
        }



    }

    public enum MovieListIntent {
        SEARCH,
        ADMIN
    }

    public enum MovieListMenuOption implements Describable {

        ADD_MOVIE("Add Movie"),
        GO_BACK("Go Back");

        private String description;
        MovieListMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
