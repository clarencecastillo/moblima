package controller;

import manager.UserManager;
import model.cinema.Staff;
import view.Describable;
import view.Menu;
import view.View;

public class AdminMenuController extends Controller {

    public static final int MAX_LOGIN_ATTEMPTS = 5;

    private static AdminMenuController instance = new AdminMenuController();

    private Menu adminMenu;

    private UserManager userManager;

    private AdminMenuController() {
        userManager = UserManager.getInstance();
    }

    public static AdminMenuController getInstance() {
        return instance;
    }

    @Override
    public void setupView() {
        adminMenu = new Menu();
        adminMenu.setTitle("Admin Menu");
        adminMenu.setContent(new String[] {
            "Please enter your credentials."
        });
        adminMenu.setMenuItems(AdminMenuOption.values());
    }

    @Override
    public void onLoad(String[] arguments) {

        adminMenu.displayHeader();
        adminMenu.display();

        Staff administrator = null;
        for (int loginAttempts = 1; loginAttempts <= MAX_LOGIN_ATTEMPTS; loginAttempts++) {
            String username = adminMenu.getString("Username");
            String password = adminMenu.getCensoredString("Password");
            if (userManager.login(username, password)) {
                administrator = userManager.findByUsername(username);
                break;
            }
            String attemptsMessage = "[" + loginAttempts + " of " + MAX_LOGIN_ATTEMPTS + "]";
            adminMenu.displayError("Access denied. Please try again. " + attemptsMessage);
        }

        if (administrator == null) {
            navigation.clearScreen();
            adminMenu.displayHeader();
            adminMenu.displayError("Max login attempts reached!");
            adminMenu.pressAnyKeyToContinue();
            navigation.goBack();
        }

        adminMenu.setContent(new String[] {
            "Signed in: " + administrator.getUsername()
        });

        navigation.clearScreen();
        adminMenu.displayHeader();
        adminMenu.displaySuccess("Access granted!");
        adminMenu.displayMenuItems();

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
                navigation.goBack();
                break;
        }
    }

    private enum AdminMenuOption implements Describable {
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
