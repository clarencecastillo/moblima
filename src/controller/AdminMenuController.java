//package controller;
//
//import controller.MovieListController.MovieListIntent;
//import manager.UserController;
//import model.cinema.Staff;
//import view.ui.Describable;
//import view.Menu;
//
//public class AdminMenuController extends Controller {
//
//    public static final int MAX_LOGIN_ATTEMPTS = 5;
//
//    private static AdminMenuController instance = new AdminMenuController();
//
//    private Staff administrator;
//
//    private Menu adminMenu;
//    private UserController userManager;
//
//    private AdminMenuController() {
//        userManager = UserController.getInstance();
//    }
//
//    public static AdminMenuController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void setupView() {
//        adminMenu = new Menu();
//        adminMenu.setMenuItems(AdminMenuOption.values());
//    }
//
//    @Override
//    public void onEnter(String[] arguments) {
//
//        if (administrator == null) {
//
//            adminMenu.setTitle("Admin Login");
//            adminMenu.displayHeader();
//
//            adminMenu.setContent("Please enter your credentials.");
//            adminMenu.displayContent();
//
//            for (int loginAttempts = 1; loginAttempts <= MAX_LOGIN_ATTEMPTS; loginAttempts++) {
//                String username = adminMenu.getString("Username");
//                String password = adminMenu.getCensoredString("Password");
//                if (userManager.login(username, password)) {
//                    administrator = userManager.findByUsername(username);
//                    break;
//                }
//                String attemptsMessage = "[" + loginAttempts + " of " + MAX_LOGIN_ATTEMPTS + "]";
//                adminMenu.displayError("Access denied. Please try again. " + attemptsMessage);
//            }
//
//            if (administrator == null) {
//                navigation.clearScreen();
//                adminMenu.displayHeader();
//                adminMenu.displayError("Max login attempts reached!");
//                adminMenu.pressAnyKeyToContinue();
//                navigation.goBack();
//            }
//        }
//
//        navigation.clearScreen();
//
//        adminMenu.setTitle("Admin Menu");
//        adminMenu.displayHeader();
//
//        adminMenu.setContent("Signed in: " + administrator.getUsername());
//        adminMenu.displayContent();
//
//        adminMenu.displaySuccess("Access granted!");
//        adminMenu.displayItems();
//
//        AdminMenuOption userChoice = AdminMenuOption.valueOf(adminMenu.getChoice());
//
//        switch (userChoice) {
//            case MANAGE_MOVIE_LISTINGS:
//                navigation.goTo(MovieListController.getInstance(),
//                                MovieListIntent.ADMIN.toString());
//                break;
//            case MANAGE_SHOWTIMES:
//                break;
//            case VIEW_REPORTS:
//                break;
//            case CONFIGURE_SETTINGS:
//                break;
//            case LOGOUT:
//                administrator = null;
//                navigation.goBack();
//                break;
//        }
//    }
//
//    private enum AdminMenuOption implements Describable {
//        MANAGE_MOVIE_LISTINGS("Manage Movie Listings"),
//        MANAGE_SHOWTIMES("Manage Showtimes"),
//        VIEW_REPORTS("View Top 5"),
//        CONFIGURE_SETTINGS("Configure Settings"),
//        LOGOUT("Log Out");
//
//        private String description;
//        AdminMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//}
