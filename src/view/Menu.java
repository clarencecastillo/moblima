package view;

import exception.InputOutOfBoundsException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Menu extends Form {

    public static final String INPUT_PROMPT = "Choose option";
    public static final String INVALID_ERROR = "Invalid user input! Please try again.";
    public static final String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";

    protected Item[] menuItems;

    public int getChoice() {
        while(true)
            try {
                return getChoiceIgnoreMismatch();
            } catch (InputMismatchException e) {
                System.out.println(UNRECOGNIZED_ERROR);
            }
    }

    public int getChoiceIgnoreMismatch() throws InputMismatchException {
        while(true)
            try {
                return getInt(INPUT_PROMPT, 1, menuItems.length) - 1;
            } catch (InputOutOfBoundsException e) {
                if ((int) e.getOutOfBoundsInput() == MenuItem.BACK_LABEL)
                    return MenuItem.BACK_LABEL;
                System.out.println(INVALID_ERROR);
            }
    }

    public void displayMenuItems() {
        for (Item menuItem : menuItems)
            menuItem.display();
        System.out.println(DASH_LINE);
    }

    public void displayMenuItemsWithBack() {
        displayMenuItemsWithBack(MenuItem.BACK_DESCRIPTION);
    }

    public void displayMenuItemsWithBack(String backOption) {
        for (Item menuItem : menuItems)
            menuItem.display();
        new MenuItem(MenuItem.BACK_LABEL, backOption).display();
        System.out.println(DASH_LINE);
    }

    public void setMenuItems(Item[] menuItems) {
        this.menuItems = menuItems;
    }

    public void setMenuItems(Describable[] describables) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (int i = 0; i < describables.length; i++) {
            String description = describables[i].getDescription();
            if (description != null)
                menuItems.add(new MenuItem(i + 1, description));
        }
        this.menuItems = menuItems.toArray(new MenuItem[menuItems.size()]);
    }
}
