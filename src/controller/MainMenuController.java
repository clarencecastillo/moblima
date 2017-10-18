package controller;

import config.AdminConfig;
import exception.InputUnrecognisedException;
import util.ConsoleColor;
import view.Line;
import view.Menu;
import view.MenuOption;
import view.View;

public class MainMenuController extends Controller {

    private static MainMenuController instance = new MainMenuController();

    private View aboutView;
    private Menu mainMenu;

    private MainMenuController() {}

    public static MainMenuController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {
        aboutView.setTitle(Line.format("MOBLIMA", Line.format(arguments[0],
                                                              ConsoleColor.GREEN)));
    }

    @Override
    public void setupView() {
        aboutView = new View();
        aboutView.setContent(new String[] {Line.wrap("TODO Description here")});
        mainMenu = new Menu("Main Menu", Menu.getDescriptions(MainMenuOption.values()));
    }

    @Override
    public View getView() {
        return aboutView;
    }

    @Override
    public void onViewDisplay() {

        mainMenu.display();

        MainMenuOption userChoice = null;
        while(true)
            try {
                userChoice = MainMenuOption.values()[mainMenu.getChoice()];
                break;
            } catch (InputUnrecognisedException e) {
                String mismatchInput = e.getMismatchInput().toString();
                if (mismatchInput.equals(AdminConfig.getAdminSecret())) {
                    userChoice = MainMenuOption.ADMIN;
                    break;
                }
                else {
                    mainMenu.displayError(Menu.UNRECOGNIZED_ERROR);
                }
            }

        switch (userChoice) {
            case SEARCH_MOVIES:
                break;
            case LIST_MOVIES:
                break;
            case VIEW_SHOWTIMES:
                break;
            case VIEW_BOOKING_HISTORY:
                break;
            case VIEW_TOP_REVIEWS:
                break;
            case VIEW_TOP_SALES:
                break;
            case ADMIN:
                navigation.goTo(AdminLoginController.getInstance());
                break;
        }
    }

    private enum MainMenuOption implements MenuOption {

        SEARCH_MOVIES("Search Movies"),
        LIST_MOVIES("List Movies"),
        VIEW_SHOWTIMES("View Showtimes"),
        VIEW_BOOKING_HISTORY("View Booking History"),
        VIEW_TOP_REVIEWS("View Top Reviews"),
        VIEW_TOP_SALES("View Top Ticket Sales"),
        ADMIN(null);

        private String description;
        MainMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
