package view.ui;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a menu view.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public abstract class MenuView extends View implements Navigable, Form {

    /**
     * The default prompt to ask for user option is "Option".
     */
    public static final String PROMPT = "Option";

    /**
     * The default display for going back is "Back".
     */
    public static final String BACK = "BACK";

    /**
     * The default display for going home is "Home".
     */
    public static final String HOME = "HOME";

    /**
     * The menu item display for going back.
     */
    public static final MenuItem BACK_MENU_ITEM = new MenuItem("Go Back", BACK);

    /**
     * The menu item display for going home.
     */
    public static final MenuItem HOME_MENU_ITEM = new MenuItem("Back to Main Menu", HOME);

    /**
     * An array list of menu items.
     */
    protected ArrayList<MenuItem> menuItems = new ArrayList<>();

    /**
     * The navigation for this view.
     */
    protected Navigation navigation;

    /**
     * Creates a menu view with the given navigation.
     * @param navigation The navigation for this view.
     */
    public MenuView(Navigation navigation) {
        this.navigation = navigation;
    }

    /**
     * Gets the menu items from this menu view.
     * @return the menu items from this menu view.
     */
    protected ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Sets the enumerable menu items for this menu view.
     * @param enumerableMenuOptions The new enumerable menu items to be set for this menu view.
     */
    protected void setMenuItems(EnumerableMenuOption... enumerableMenuOptions) {
        menuItems.clear();
        for (int i = 0; i < enumerableMenuOptions.length; i++) {
            String description = enumerableMenuOptions[i].getDescription();
            if (description != null)
                menuItems.add(new MenuItem(description, enumerableMenuOptions[i].name()));
        }
    }

    /**
     * Sets the menu items for this menu view.
     * @param menuItems The new menu items to be set for this menu view.
     */
    protected void setMenuItems(MenuItem[] menuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    /**
     * Adds a go back option to this menu view.
     */
    protected void addBackOption() {
        if (menuItems.contains(BACK_MENU_ITEM))
            return;
        menuItems.add(BACK_MENU_ITEM);
    }

    /**
     * Adds a go home option to this menu view.
     */
    protected void addHomeOption() {
        menuItems.add(HOME_MENU_ITEM);
    }

    /**
     * Displays menu items.
     */
    public void displayItems() {
        char itemIndex = 'A';
        for (int i = 0; i < menuItems.size(); i++, itemIndex++)
            menuItems.get(i).display(itemIndex);
    }

    /**
     * Displays menu items.
     */
    @Override
    public void display() {
        super.display();
        displayItems();
        System.out.println();
    }

    /**
     * Gets the choice of the menu items from the user.
     * @return the choice of the menu items from the user.
     */
    public String getChoice() {
        int index = Form.getChar(PROMPT, 'A', (char) ('A' + menuItems.size() - 1)) - 'A';
        return menuItems.get(index).getValue();
    }
}
