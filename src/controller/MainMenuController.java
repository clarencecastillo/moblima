//package controller;
//
//import view.MainMenuView;
//
//public class MainMenuController extends Controller {
//
//    private static MainMenuController instance = new MainMenuController();
//
//    private String version;
//
//    private MainMenuView mainMenuView;
//
//    private MainMenuController() { }
//
//    public static MainMenuController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void setupView() {
//
//
//    }
//
//    @Override
//    public void onEnter(String[] arguments) {
//
//        version = arguments[0];
//        mainMenuView = new MainMenuView(version);
//
//
//    }
//}
