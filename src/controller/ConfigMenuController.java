package controller;

import view.Describable;
import view.Menu;

public class ConfigMenuController extends Controller {

    private static ConfigMenuController instance = new ConfigMenuController();

    private Menu configMenu;


    private ConfigMenuController() {}

    public static ConfigMenuController getInstance() {
        return instance;
    }


    @Override
    public void setupView() {
        configMenu = new Menu();
        configMenu.setTitle("Configuration Setting Menu");
        configMenu.setMenuItems(ConfigMenuOption.values());
    }


    @Override
    public void onLoad(String[] arguments) {

        configMenu.displayHeader();
        configMenu.displayItems();


        ConfigMenuOption userChoice = ConfigMenuOption.valueOf(configMenu.getChoiceIgnoreMismatch());

        switch (userChoice) {
            case HOLIDAY:
                break;
            case PRICING:
                Menu pricingSettingMenu = new Menu();
                pricingSettingMenu.setTitle("Pricing Setting Menu");
                pricingSettingMenu.setMenuItems(PricingSettingMenuOption.values());

                pricingSettingMenu.displayHeader();
                pricingSettingMenu.displayItems();
                PricingSettingMenuOption pricingSettingChoice = PricingSettingMenuOption.valueOf(pricingSettingMenu.getChoice());

                switch (pricingSettingChoice) {
                    case TICKET_TYPE_PRICING:
                        break;
                    case CINEMA_TYPE_PRICING:
                        break;
                    case MOVIE_TYPE_PRICING:
                        break;
                    case SEAT_TYPE_PRICING:
                        break;
                }
                break;
            case CINEMA:
                Menu cinemaSettingMenu = new Menu();
                cinemaSettingMenu.setTitle("Cinema Setting Menu");
                cinemaSettingMenu.setMenuItems(CinemaSettingMenuOption.values());
                cinemaSettingMenu.displayHeader();
                cinemaSettingMenu.displayItems();

                CinemaSettingMenuOption cinemaSettingChoice = CinemaSettingMenuOption.valueOf(cinemaSettingMenu.getChoice());
                switch (cinemaSettingChoice) {
                    case REGULAR:
                        break;
                    case PLATINUM:
                        break;
                    case EXECUTIVE:
                        break;
                }
                break;
            case BOOKING:
                break;
            case PAYMENT:
                break;
            case ADMIN:
                break;
        }
    }

    private enum ConfigMenuOption implements Describable {

        HOLIDAY("Holiday"),
        PRICING("Pricing"),
        CINEMA("Cinema"),
        BOOKING("Booking"),
        PAYMENT("Payment"),
        ADMIN("Admin");

        private String description;
        ConfigMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    private enum PricingSettingMenuOption implements Describable {

        TICKET_TYPE_PRICING("Ticket Type Pricing"),
        CINEMA_TYPE_PRICING("Cinema Type Pricing"),
        MOVIE_TYPE_PRICING("Movie Type Pricing"),
        SEAT_TYPE_PRICING("Seat Type Pricing");

        private String description;
        PricingSettingMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    private enum CinemaSettingMenuOption implements Describable {

        REGULAR("Regular"),
        PLATINUM("Platinum"),
        EXECUTIVE("Executive");

        private String description;
        CinemaSettingMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

}


