package controller;

import java.util.ArrayList;
import manager.MovieManager;
import model.movie.Movie;
import util.ConsoleColor;
import view.Line;
import view.ListMenu;
import view.View;

public class MovieListController extends Controller {

    private static MovieListController instance = new MovieListController();

    private View movieListView;
    private ListMenu movieListMenu;

    private MovieManager movieManager;

    private MovieListController() {
        movieManager = MovieManager.getInstance();
    }

    public static MovieListController getInstance() {
        return instance;
    }

    private View generateView(Movie movie) {
        View movieView = new View();
        String title = Line.format(String.format("%s [%s] %s", movie.getTitle(),
                                                 Line.format(movie.getType().toString(),
                                                             ConsoleColor.GREEN),
                                                 movie.getRating()),
                                   movie.getStatus().toString());
        movieView.setTitle(title);
        movieView.setContent(new String[] {
            Line.format("Director: " + movie.getDirector().getFullName(), "Rating: "),
//            Line.format("Actors: " + String.join(",", movie.getActors())))
        });
        return movieView;
    }

    @Override
    public void onLoad(String[] arguments) {

        ArrayList<View> items = new ArrayList<>();

        String searchFilter = arguments[0];
        if (searchFilter != null) {
            movieListMenu.setTitle("Search Results");
//            for (Movie movie : movieManager.findByKeyword(searchFilter))
//                items.add(new View(movie.getTitle(), ))
        }

//        movieListMenu.setItems();

    }

    @Override
    public void setupView() {
        movieListView = new View();
    }

    @Override
    public View getView() {
        return movieListView;
    }

    @Override
    public void onViewDisplay() {

    }
}
