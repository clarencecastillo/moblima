package controller;

import manager.UserManager;
import model.cinema.Staff;
import util.ConsoleColor;
import view.Line;
import view.Menu;
import view.MenuOption;
import view.View;

public class AdminMenuController extends Controller {

    private static AdminMenuController instance = new AdminMenuController();

    private Menu adminMenu;
    private UserManager userManager;

    private Staff administrator;

    private AdminMenuController() {
        userManager = UserManager.getInstance();
    }

    public static AdminMenuController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {
        administrator = userManager.findByUsername(arguments[0]);
        String title = Line.format("Admin Menu",
                                   Line.format(administrator.getUsername(), ConsoleColor.GREEN));
        adminMenu.setTitle(title);
    }

    @Override
    public void setupView() {
        adminMenu = new Menu();
        adminMenu.setContent(Menu.getDescriptions(AdminMenuOption.values()));
    }

    @Override
    public View getView() {
        return adminMenu;
    }

    @Override
    public void onViewDisplay() {

        AdminMenuOption userChoice = AdminMenuOption.values()[adminMenu.getChoice()];

        switch (userChoice) {
            case MANAGE_MOVIE_LISTINGS:
                break;
            case MANAGE_SHOWTIMES:
                break;
            case MANAGE_CINEPLEXES:
                break;
            case MANAGE_CINEMAS:
                break;
            case VIEW_REPORTS:
                break;
            case CONFIGURE_SETTINGS:
                break;
            case LOGOUT:
                navigation.goBack(2);
                break;
        }
    }

    private enum AdminMenuOption implements MenuOption {
        MANAGE_MOVIE_LISTINGS("Search Movies"),
        MANAGE_SHOWTIMES("List Movies"),
        MANAGE_CINEPLEXES("View Showtimes"),
        MANAGE_CINEMAS("View Booking History"),
        VIEW_REPORTS("View Top Reviews"),
        CONFIGURE_SETTINGS("View Top Ticket Sales"),
        LOGOUT("Logout");

        private String description;
        AdminMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
