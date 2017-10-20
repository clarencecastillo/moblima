package controller;

import config.AdminConfig;
import controller.MovieListController.MovieListIntent;
import exception.InputUnrecognisedException;
import view.Describable;
import view.MainMenu;
import view.MainMenu.MainMenuOption;
import view.Menu;
import view.View;

public class MainMenuController extends Controller {

    private static MainMenuController instance = new MainMenuController();

    private String version;

    private MainMenu mainMenu;

    private MainMenuController() { }

    public static MainMenuController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {


    }

    @Override
    public void onEnter(String[] arguments) {

        version = arguments[0];
        mainMenu = new MainMenu(version);


    }
}
