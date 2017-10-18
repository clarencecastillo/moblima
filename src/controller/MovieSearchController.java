package controller;

import view.Form;
import view.View;

public class MovieSearchController extends Controller {

    private static MovieSearchController instance = new MovieSearchController();
    private Form movieSearchForm;

    private MovieSearchController() { }

    public static MovieSearchController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {

    }

    @Override
    public void setupView() {
        movieSearchForm = new Form("Search Movies");
        movieSearchForm.setContent(new String[] {
            "Please enter search terms. Keywords may include movie title, director, and actors."
        });
    }

    @Override
    public View getView() {
        return movieSearchForm;
    }

    @Override
    public void onViewDisplay() {
        String input = movieSearchForm.getString("Search");

    }
}
