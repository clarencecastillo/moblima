package view.ui;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MenuView extends View implements Navigable, Form {

    public static final String PROMPT = "Option";

    public static final String BACK = "BACK";
    public static final String HOME = "HOME";
    public static final MenuItem BACK_MENU_ITEM = new MenuItem("Go Back", BACK);
    public static final MenuItem HOME_MENU_ITEM = new MenuItem("Back to Main Menu", HOME);

    protected ArrayList<MenuItem> menuItems = new ArrayList<>();
    protected Navigation navigation;

    public MenuView(Navigation navigation) {
        this.navigation = navigation;
    }

    protected ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    protected void setMenuItems(EnumerableMenuOption... enumerableMenuOptions) {
        menuItems.clear();
        for (int i = 0; i < enumerableMenuOptions.length; i++) {
            String description = enumerableMenuOptions[i].getDescription();
            if (description != null)
                menuItems.add(new MenuItem(description, enumerableMenuOptions[i].name()));
        }
    }

    protected void setMenuItems(MenuItem[] menuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    protected void addBackOption() {
        menuItems.add(BACK_MENU_ITEM);
    }

    protected void addHomeOption() {
        menuItems.add(HOME_MENU_ITEM);
    }

    public void displayItems() {
        char itemIndex = 'A';
        for (int i = 0; i < menuItems.size(); i++, itemIndex++)
            menuItems.get(i).display(itemIndex);
    }

    @Override
    public void display() {
        super.display();
        displayItems();
        System.out.println();
    }

    public String getChoice() {
        int index = Form.getChar(PROMPT, 'A', (char) ('A' + menuItems.size() - 1)) - 'A';
        return menuItems.get(index).getValue();
    }
}
