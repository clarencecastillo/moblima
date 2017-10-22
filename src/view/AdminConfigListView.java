package view;

import config.AdminConfig;
import view.ui.*;

import java.util.ArrayList;

public class AdminConfigListView extends ListView {

    private AdminConfig adminConfig;

    public AdminConfigListView(Navigation navigation) {
        super(navigation);
        this.adminConfig = AdminConfig.getInstance();
    }

    @Override
    public void onLoad(NavigationIntent intent, String... args) {
        setTitle("Administration Config");
        setContent("Select the item to change.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        viewItems.add(new ViewItem("Admin Secret",
                AdminConfigMenuOption.ADMIN_SECRET.toString(),
                AdminConfig.getAdminSecret()));
        setViewItems(viewItems.toArray(new ViewItem[viewItems.size()]));

        display();

        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            switch (AdminConfigMenuOption.valueOf(userChoice)) {
                case ADMIN_SECRET:
                    View.displayInformation("Please enter new admin secret.");
                    String newAdminSecret = Form.getString("Admin Secret");
                    adminConfig.setAdminSecret(newAdminSecret);
                    break;
            }
            View.displaySuccess("Successfully changed admin secret!");
            Form.pressAnyKeyToContinue();
            navigation.reload();
        }
    }

    private enum AdminConfigMenuOption {
        ADMIN_SECRET
    }
}
