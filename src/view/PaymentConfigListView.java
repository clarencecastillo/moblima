package view;

import config.PaymentConfig;
import exception.UnauthorisedNavigationException;
import view.ui.*;

import java.util.ArrayList;

/**
 * This moblima.view displays the user interface for the user to configure payment settings.
 *
 * @version 1.0
 * @since 2017-10-30
 */

public class PaymentConfigListView extends ListView {

    private PaymentConfig paymentConfig;

    public PaymentConfigListView(Navigation navigation) {
        super(navigation);
        this.paymentConfig = PaymentConfig.getInstance();
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        if (accessLevel != AccessLevel.ADMINISTRATOR)
            throw new UnauthorisedNavigationException();

        setTitle("Payment Config");
        setContent("Select the item to change.");
        addBackOption();
    }

    @Override
    public void onEnter() {
        ArrayList<ViewItem> viewItems = new ArrayList<>();
        viewItems.add(new ViewItem("GST", (int) (PaymentConfig.getGst() * 100) + "%",
                PaymentConfigListOption.GST.toString()));
//        viewItems.add(new ViewItem("Allow Refunds",
//                PaymentConfigListOption.ALLOW_REFUNDS.toString(),
//                PaymentConfig.isRefundsAllowed() ? "ALLOWED" : "NOT ALLOWED"));
        setViewItems(viewItems);

        display();
        String userChoice = getChoice();
        if (userChoice.equals(BACK))
            navigation.goBack();
        else {
            switch (PaymentConfigListOption.valueOf(userChoice)) {
                case GST:
                    View.displayInformation("Please enter new value. Enter values in SGD.");
                    double newGst = Form.getDouble("New GST", 0, 5);
                    paymentConfig.setGst(newGst);
                    break;
//                case ALLOW_REFUNDS:
//                    paymentConfig.setAllowedRefunds(!PaymentConfig.isRefundsAllowed());
//                    break;
            }
            View.displaySuccess("Successfully changed payment moblima.config value!");
            Form.pressAnyKeyToContinue();
            navigation.reload(AccessLevel.ADMINISTRATOR);
        }
    }

    private enum PaymentConfigListOption {
        GST,
//        ALLOW_REFUNDS
    }
}
