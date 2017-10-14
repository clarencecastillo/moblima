package controller;

import manager.UserManager;

public class AdminController {

    private static AdminController instance;

    private UserManager userManager;

    private AdminController(UserManager userManager) {
        this.userManager = userManager;
    }
}
