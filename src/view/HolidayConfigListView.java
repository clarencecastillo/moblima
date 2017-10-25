package view;

import config.HolidayConfig;
import exception.UnauthorisedNavigationException;
import util.Utilities;
import view.ui.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeSet;

public class HolidayConfigListView extends ListView {


    private HolidayConfig holidayConfig;

    private Hashtable<Date, String> holidays;

    public HolidayConfigListView(Navigation navigation) {
        super(navigation);
        holidayConfig = HolidayConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Holiday Config");
        holidays = HolidayConfig.getHolidays();

        setMenuItems(HolidayConfigListOption.values());
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        TreeSet<Date> holidayDates = new TreeSet<>(holidays.keySet());
        for (Date date : holidayDates) {
            String holidayDate = Utilities.toFormat(date, "d MMMMM");
            viewItems.add(new ViewItem(holidayDate, Utilities.toFormat(date), holidays.get(date)));
        }
        setViewItems(viewItems);

        display();
        String userOption = getChoice();
        if (userOption.equals(BACK))
            navigation.goBack();
        else {
            try {
                switch (HolidayConfigListOption.valueOf(userOption)) {
                    case SET_HOLIDAY:
                        View.displayInformation("Please enter holiday details. Specifying dates already " +
                                "configured will be overwritten.");
                        Date date = Form.getDate("Enter date", "dd/MM");
                        String description = Form.getString("Holiday name");
                        holidayConfig.setHoliday(date, description);
                        View.displaySuccess("Successfully added new holiday!");
                        Form.pressAnyKeyToContinue();
                        break;
                }
            } catch (IllegalArgumentException e) {
                Date holidayDate = Utilities.parseDate(userOption);
                navigation.goTo(new HolidayMenuView(navigation), AccessLevel.ADMINISTRATOR, userOption, holidays.get(holidayDate));
            }
            navigation.reload(AccessLevel.ADMINISTRATOR);
        }
    }

    private enum HolidayConfigListOption implements EnumerableMenuOption {

        SET_HOLIDAY("Set Holiday");

        private String description;

        HolidayConfigListOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}
