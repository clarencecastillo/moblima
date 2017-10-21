//package controller;
//
//import view.ui.Describable;
//
//public class MovieReviewViewController extends Controller {
//
//    private static MovieReviewViewController instance = new MovieReviewViewController();
//
//    private MovieReviewViewController() {
//    }
//
//    public static MovieReviewViewController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void setupView() {
//
////                View reviewerLogInView;
////                reviewerLogInView = new View("Reviewer Log In Menu");
////                Menu reviewerLogInMenu = new Menu();
////                reviewerLogInMenu.setContent(Menu.getDescriptions(ReviewerLogInMenuOption.values()));
////                reviewerLogInMenu.displayContent();
////
////                ReviewerLogInMenuOption userLogInChoice = ReviewerLogInMenuOption.values()[reviewerLogInMenu.getChoice()];
////                switch (userLogInChoice) {
////                    case LOG_IN_VIA_PHONE_NUMBER:
////                        break;
////                    case LOG_IN_VIA_EMAIL:
////                        break;
////                    case ANONYMOUS:
////                        break;
////                }
////                break;
//    }
//
//    @Override
//    public void onEnter(String[] arguments) {
//
//    }
//
//    private enum MovieReviewLoginMenuOption implements Describable {
//
//        LOG_IN_VIA_PHONE_NUMBER("Log in via phone number"),
//        LOG_IN_VIA_EMAIL("Log in via email"),
//        ANONYMOUS("Anonymous");
//
//        private String description;
//        MovieReviewLoginMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//}
