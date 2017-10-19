//package controller;
//
//import view.Menu;
//import view.View;
//import view.MenuOption;
//
//public class AdminMovieViewController extends MovieViewController {
//
//    private static AdminMovieViewController instance = new AdminMovieViewController();
//
//    private AdminMovieViewController() {}
//
//    public static AdminMovieViewController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void setupView() {
//        super.setupView();
//        movieMenu.setContent(Menu.getDescriptions(AdminMovieMenuOption.values()));
//    }
//
//    @Override
//    public void onViewDisplay() {
//
//        movieMenu.display();
//
//        AdminMovieMenuOption userChoice = AdminMovieMenuOption.values()[movieMenu.getChoice()];
//
//        switch (userChoice) {
//            case UPDATE:
//                break;
//            case REMOVE:
//                break;
//        }
//    }
//
//    private enum AdminMovieMenuOption implements MenuOption {
//
//        UPDATE("Update"),
//        REMOVE("Remove");
//
//        private String description;
//        AdminMovieMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//}
