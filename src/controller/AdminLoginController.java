package controller;

import manager.UserManager;
import view.Form;
import view.Line;
import view.View;

public class AdminLoginController extends Controller {

    public static final int MAX_LOGIN_ATTEMPTS = 5;

    private static AdminLoginController instance = new AdminLoginController();

    private Form loginForm;
    private UserManager userManager;

    private AdminLoginController() {
        userManager = UserManager.getInstance();
    }

    public static AdminLoginController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {

    }

    @Override
    public void setupView() {
        loginForm = new Form("Admin Login");
    }

    @Override
    public View getView() {
        return loginForm;
    }

    @Override
    public void onViewDisplay() {

        for (int i = 0; i < MAX_LOGIN_ATTEMPTS; i++) {
            String username = loginForm.getString("Username");
            String password = loginForm.getCensoredString("Password");
            if (userManager.login(username, password)) {
                loginForm.displaySuccess("Access granted!");
                navigation.goTo(AdminMenuController.getInstance(), username);
                return;
            }
            loginForm.displayError(
                Line.format("Access denied. Please try again",
                            "[" + (i + 1) + " of " + MAX_LOGIN_ATTEMPTS + "]"));
        }

        navigation.goBack();
    }
}
