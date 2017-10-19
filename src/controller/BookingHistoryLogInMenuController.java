//package controller;
//
//import view.Menu;
//import view.MenuOption;
//import view.View;
//
//public class BookingHistoryLogInMenuController extends Controller {
//
//    private static BookingHistoryLogInMenuController instance = new BookingHistoryLogInMenuController();
//
//    private View bookingHistoryLogInView;
//    private Menu bookingHistoryLogInMenu;
//
//
//    private BookingHistoryLogInMenuController() {}
//
//    public static BookingHistoryLogInMenuController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void onLoad(String[] arguments) {}
//
//    @Override
//    public void setupView() {
//        bookingHistoryLogInView = new View("Booking History Log In View");
//        bookingHistoryLogInMenu = new Menu();
//        bookingHistoryLogInMenu.setContent(Menu.getDescriptions(BookingHistoryLogInMenuOption.values()));
//    }
//
//    @Override
//    public View getView() {
//        return bookingHistoryLogInView;
//    }
//
//    @Override
//    public void onViewDisplay() {
//
//        bookingHistoryLogInMenu.displayContent();
//
//        BookingHistoryLogInMenuOption userChoice = BookingHistoryLogInMenuOption.values()[bookingHistoryLogInMenu.getChoice()];
//
//        switch (userChoice) {
//            case LOG_IN_VIA_PHONE_NUMBER:
//                break;
//            case LOG_IN_VIA_EMAIL:
//                break;
//        }
//    }
//
//    private enum BookingHistoryLogInMenuOption implements MenuOption {
//
//        LOG_IN_VIA_PHONE_NUMBER("Log in via phone number"),
//        LOG_IN_VIA_EMAIL("Log in via email");
//
//        private String description;
//        BookingHistoryLogInMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//}
//
//
