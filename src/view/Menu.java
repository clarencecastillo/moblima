package view;

import exception.InputOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Menu extends Form {

    public static final String INPUT_PROMPT = "Choose option";
    public static final String INVALID_ERROR = "Invalid user input! Please try again.";
    public static final String UNRECOGNIZED_ERROR = "Unrecognized user input! Please try again.";

//    public static final MenuItem BACK = new MenuItem("Go Back");
//    public static final int BACK_INDEX = 0;

    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private ArrayList<ViewItem> viewItems = new ArrayList<>();

    public String getChoice() {
        while(true)
            try {
                return getChoiceIgnoreMismatch();
            } catch (InputMismatchException e) {
                System.out.println(UNRECOGNIZED_ERROR);
            }
    }

    public String getChoiceIgnoreMismatch() throws InputMismatchException {
        int totalItems = menuItems.size() + viewItems.size();
        while(true)
            try {
                int index = getInt(INPUT_PROMPT, 1, totalItems) - 1;
                return index < viewItems.size() ?
                       viewItems.get(index).getValue() :
                       menuItems.get(index - viewItems.size()).getValue();
            } catch (InputOutOfBoundsException e) {
                System.out.println(INVALID_ERROR);
            }
    }

    public void displayItems() {
        int itemIndex = 1;
        for (int i = 0; i < viewItems.size(); i++, itemIndex++)
            viewItems.get(i).display(itemIndex);
        if (viewItems.size() > 0)
            System.out.println();
        for (int i = 0; i < menuItems.size(); i++, itemIndex++)
            menuItems.get(i).display(itemIndex);

    }

    public void setMenuItems(MenuItem[] menuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(Arrays.asList(menuItems));
    }

    public void setMenuItems(Describable[] describables) {
        menuItems.clear();
        for (int i = 0; i < describables.length; i++) {
            String description = describables[i].getDescription();
            if (description != null)
                menuItems.add(new MenuItem(description, describables[i].toString()));
        }
    }

    public void setViewItems(ViewItem[] viewItems) {
        this.viewItems.clear();
        this.viewItems.addAll(Arrays.asList(viewItems));
    }
}
