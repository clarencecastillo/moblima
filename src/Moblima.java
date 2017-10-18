import controller.AdminLoginController;
import controller.AdminMenuController;
import controller.AudienceController;
import controller.Controller;
import controller.MainMenuController;
import controller.Navigation;
import java.util.ArrayList;
import manager.UserManager;

public class Moblima {

    public static final String VERSION = "v1.0.0";

    private Navigation nav;
    private ArrayList<Controller> controllers;
    private Controller rootController;

    public Moblima() {
        controllers = new ArrayList<>();
        bootstrap();
    }

    public void bootstrap() {

        // DEBUG
        UserManager userManager = UserManager.getInstance();
        userManager.registerStaff("Anqi", "Tu", "91005071",
                                  "tuanqi@cinema.com", "tuanqi",  "513628");

        controllers.add(MainMenuController.getInstance());
        controllers.add(AdminLoginController.getInstance());
        controllers.add(AdminMenuController.getInstance());
        controllers.add(AudienceController.getInstance());

        nav = new Navigation();
        for (Controller controller: controllers)
            controller.init(nav);

        rootController = controllers.get(0);
    }

    public void run() {
        nav.goTo(rootController, VERSION);
    }
}
