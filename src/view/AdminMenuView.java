package view;

import exception.NavigationRejectedException;
import manager.UserController;
import model.cinema.Staff;
import view.MovieListView.MovieListIntent;
import view.ui.Describable;
import view.ui.Form;
import view.ui.MenuView;
import view.ui.Navigation;
import view.ui.NavigationIntent;
import view.ui.View;

public class AdminMenuView extends MenuView {

    public static final int MAX_LOGIN_ATTEMPTS = 5;

    private Staff administrator;

    private UserController userController;

    public AdminMenuView(Navigation navigation) {
        super(navigation);
        userController = UserController.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        setTitle("Admin Menu");
        setMenuItems(AdminMenuOption.values());
        View.displayInformation("Please enter your credentials");

        for (int loginAttempts = 1; loginAttempts <= MAX_LOGIN_ATTEMPTS; loginAttempts++) {
            String username = Form.getString("Username");
            String password = Form.getCensoredString("Password");
            if (userController.login(username, password)) {
                administrator = userController.findByUsername(username);
                break;
            }
            String attemptsMessage = "[" + loginAttempts + " of " + MAX_LOGIN_ATTEMPTS + "]";
            View.displayError("Access denied. Please try again. " + attemptsMessage);
        }

        if (administrator == null) {
            View.displayError("Max login attempts reached!");
            Form.pressAnyKeyToContinue();
            throw new NavigationRejectedException();
        }

        setContent("Signed in: " + administrator.getUsername());
        View.displaySuccess("Access granted!");
    }

    @Override
    public void onEnter() {
        display();
        AdminMenuOption userChoice = AdminMenuOption.valueOf(getChoice());
        switch (userChoice) {
            case MANAGE_MOVIE_LISTINGS:
                navigation.goTo(new MovieListView(navigation), MovieListIntent.ADMIN);
                break;
            case MANAGE_SHOWTIMES:
                break;
            case VIEW_REPORTS:
                break;
            case CONFIGURE_SETTINGS:
                navigation.goTo(new ConfigMenuView(navigation));
                break;
            case LOGOUT:
                View.displaySuccess("You have been logged out successfully!");
                Form.pressAnyKeyToContinue();
                navigation.goBack();
                break;
        }
    }

    private enum AdminMenuOption implements Describable {
        MANAGE_MOVIE_LISTINGS("Manage Movie Listings"),
        MANAGE_SHOWTIMES("Manage Showtimes"),
        VIEW_REPORTS("View Top 5"),
        CONFIGURE_SETTINGS("Configure Settings"),
        LOGOUT("Log Out");

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
