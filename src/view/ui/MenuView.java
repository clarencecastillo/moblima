package view.ui;

import exception.InputOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public abstract class MenuView extends View implements Navigable, Form {

    public static final String INPUT_PROMPT = "Choose option";
    public static final String INVALID_ERROR = "Invalid user input! Please try again.";
    public static final String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";

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

    protected void setMenuItems(MenuItem[] menuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    protected void setMenuItems(Describable... describables) {
        menuItems.clear();
        for (int i = 0; i < describables.length; i++) {
            String description = describables[i].getDescription();
            if (description != null)
                menuItems.add(new MenuItem(description, describables[i].toString()));
        }
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
        while(true)
            try {
                return getChoiceIgnoreMismatch();
            } catch (InputMismatchException e) {
                View.displayError(UNRECOGNIZED_ERROR);
            }
    }

    public String getChoiceIgnoreMismatch() {
        while(true)
            try {
                int index = Form.getChar(INPUT_PROMPT, 'A',
                                         (char) ('A' + menuItems.size() - 1)) - 'A';
                return menuItems.get(index).getValue();
            } catch (InputOutOfBoundsException e) {
                View.displayError(INVALID_ERROR);
            }
    }
}