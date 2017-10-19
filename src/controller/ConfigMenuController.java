//package controller;
//
//import view.Menu;
//import view.MenuOption;
//import view.View;
//
//public class ConfigMenuController extends Controller {
//
//    private static ConfigMenuController instance = new ConfigMenuController();
//
//    private View configView;
//    private Menu configMenu;
//
//
//    private ConfigMenuController() {}
//
//    public static ConfigMenuController getInstance() {
//        return instance;
//    }
//
//    @Override
//    public void onLoad(String[] arguments) {}
//
//    @Override
//    public void setupView() {
//        configView = new View("Configure Setting Menu");
//        configMenu = new Menu();
//        configMenu.setContent(Menu.getDescriptions(ConfigMenuOption.values()));
//    }
//
//    @Override
//    public View getView() {
//        return configView;
//    }
//
//    @Override
//    public void onViewDisplay() {
//
//        configMenu.display();
//
//        ConfigMenuOption userChoice = ConfigMenuOption.values()[configMenu.getChoice()];
//
//        switch (userChoice) {
//            case HOLIDAY:
//                break;
//            case PRICING:
//                Menu pricingSettingMenu;
//                pricingSettingMenu = new Menu();
//                pricingSettingMenu.setContent(Menu.getDescriptions(PricingSettingMenuOption.values()));
//                pricingSettingMenu.display();
//
//                PricingSettingMenuOption pricingSettingChoice = PricingSettingMenuOption.values()[pricingSettingMenu.getChoice()];
//                switch (pricingSettingChoice) {
//                    case TICKET_TYPE_PRICING:
//                        break;
//                    case CINEMA_TYPE_PRICING:
//                        break;
//                    case MOVIE_TYPE_PRICING:
//                        break;
//                    case SEAT_TYPE_PRICING:
//                        break;
//                }
//                break;
//            case CINEMA:
//                Menu cinemaSettingMenu;
//                cinemaSettingMenu = new Menu();
//                cinemaSettingMenu.setContent(Menu.getDescriptions(CinemaSettingMenuOption.values()));
//                cinemaSettingMenu.display();
//
//                CinemaSettingMenuOption cinemaSettingChoice = CinemaSettingMenuOption.values()[cinemaSettingMenu.getChoice()];
//                switch (cinemaSettingChoice) {
//                    case REGULAR:
//                        break;
//                    case PLATINUM:
//                        break;
//                    case EXECUTIVE:
//                        break;
//                }
//                break;
//            case BOOKING:
//                break;
//            case PAYMENT:
//                break;
//            case ADMIN:
//                break;
//        }
//    }
//
//    private enum ConfigMenuOption implements MenuOption {
//
//        HOLIDAY("Holiday"),
//        PRICING("Pricing"),
//        CINEMA("Cinema"),
//        BOOKING("Booking"),
//        PAYMENT("Payment"),
//        ADMIN("Admin");
//
//        private String description;
//        ConfigMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//
//    private enum PricingSettingMenuOption implements MenuOption {
//
//        TICKET_TYPE_PRICING("Ticket Type Pricing"),
//        CINEMA_TYPE_PRICING("Cinema Type Pricing"),
//        MOVIE_TYPE_PRICING("Movie Type Pricing"),
//        SEAT_TYPE_PRICING("Seat Type Pricing");
//
//        private String description;
//        PricingSettingMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//
//    private enum CinemaSettingMenuOption implements MenuOption {
//
//        REGULAR("Regular"),
//        PLATINUM("Platinum"),
//        EXECUTIVE("Executive");
//
//        private String description;
//        CinemaSettingMenuOption(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//
//
//}
//
//
