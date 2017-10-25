package view;

import config.HolidayConfig;
import exception.UnauthorisedNavigationException;
import util.Utilities;
import view.ui.*;

import java.util.Date;

public class HolidayMenuView extends MenuView {

    private Date holidayDate;
    private String holidayDescription;

    private HolidayConfig holidayConfig;

    public HolidayMenuView(Navigation navigation) {
        super(navigation);
        this.holidayConfig = HolidayConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        this.holidayDate = Utilities.parseDate(args[0]);
        this.holidayDescription = args[1];

        setTitle("Holiday Config");
        setContent("Date: " + Utilities.toFormat(holidayDate, "d MMMMM"),
                "Description: " + holidayDescription);
        setMenuItems(HolidayMenuOption.values());
        addBackOption();
    }

    @Override
    public void onEnter() {
        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else
            switch (HolidayMenuOption.valueOf(userChoice)) {
                case REMOVE:
                    holidayConfig.unsetHoliday(holidayDate);
                    View.displaySuccess("Successfully removed holiday date!");
                    Form.pressAnyKeyToContinue();
                    navigation.goBack();
                    break;
            }
    }

    private enum HolidayMenuOption implements EnumerableMenuOption {

        REMOVE("Remove Holiday");

        private String description;

        HolidayMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
