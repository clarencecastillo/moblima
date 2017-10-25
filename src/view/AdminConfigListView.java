package view;

import config.AdminConfig;
import exception.UnauthorisedNavigationException;
import view.ui.*;

import java.util.ArrayList;

public class AdminConfigListView extends ListView {

    private AdminConfig adminConfig;

    public AdminConfigListView(Navigation navigation) {
        super(navigation);
        this.adminConfig = AdminConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Administration Config");
        setContent("Select the item to change.");
        addBackOption();
    }

    @Override
    public void onEnter() {

        ArrayList<ViewItem> viewItems = new ArrayList<>();
        viewItems.add(new ViewItem("Admin Secret",
                AdminConfigListOption.ADMIN_SECRET.toString(),
                AdminConfig.getAdminSecret()));
        setViewItems(viewItems);

        display();

        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            switch (AdminConfigListOption.valueOf(userChoice)) {
                case ADMIN_SECRET:
                    View.displayInformation("Please enter new admin secret.");
                    String newAdminSecret = Form.getString("Admin Secret");
                    adminConfig.setAdminSecret(newAdminSecret);
                    break;
            }
            View.displaySuccess("Successfully changed admin secret!");
            Form.pressAnyKeyToContinue();
            navigation.reload(AccessLevel.ADMINISTRATOR);
        }
    }

    private enum AdminConfigListOption {
        ADMIN_SECRET
    }
}
