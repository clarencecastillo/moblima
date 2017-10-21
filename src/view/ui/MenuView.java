package view.ui;

import exception.InputOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public abstract class MenuView extends View implements Navigable, Form {

    public static final String INPUT_PROMPT = "Choose option";
    public static final String INVALID_ERROR = "Invalid user input! Please try again.";
    public static final String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";

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

    protected void setMenuItems(Describable[] describables) {
        menuItems.clear();
        for (int i = 0; i < describables.length; i++) {
            String description = describables[i].getDescription();
            if (description != null)
                menuItems.add(new MenuItem(description, describables[i].toString()));
        }
    }

    public void displayItems() {
        int itemIndex = 1;
        for (int i = 0; i < menuItems.size(); i++, itemIndex++)
            menuItems.get(i).display(itemIndex);
        System.out.println();
    }

    @Override
    public void display() {
        super.display();
        displayItems();
    }

    public String getChoice() {
        while(true)
            try {
                return getChoiceIgnoreMismatch();
            } catch (InputMismatchException e) {
                System.out.println(UNRECOGNIZED_ERROR);
            }
    }

    public String getChoiceIgnoreMismatch() {
        while(true)
            try {
                int index = Form.getInt(INPUT_PROMPT, 1, menuItems.size()) - 1;
                return menuItems.get(index).getValue();
            } catch (InputOutOfBoundsException e) {
                System.out.println(INVALID_ERROR);
            }
    }
}
