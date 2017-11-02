package view.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a list view.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public abstract class ListView extends MenuView implements Navigable {

    /**
     * An array list of the view items to be displayed in this list view.
     */
    private ArrayList<ViewItem> viewItems = new ArrayList<>();

    /**
     * Creates a list view with the given navigation.
     * @param navigation
     */
    public ListView(Navigation navigation) {
        super(navigation);
    }

    /**
     * Gets the view items from this list view.
     * @return the view items from this list view.
     */
    protected ArrayList<ViewItem> getViewItems() {
        return viewItems;
    }

    /**
     * Sets the view items for this menu view.
     * @param viewItems The view items for this menu view.
     */
    protected void setViewItems(List<ViewItem> viewItems) {
        this.viewItems.clear();
        this.viewItems.addAll(viewItems);
    }

    /**
     * Displays view items.
     */
    @Override
    public void displayItems() {
        if (viewItems.size() == 0)
            System.out.println("No items to show.");
        else
            for (int i = 0; i < viewItems.size(); i++) {
                viewItems.get(i).display(i + 1);
                System.out.println();
            }
        if (menuItems.size() > 0) {
            System.out.println();
            super.displayItems();
        }
    }

    /**
     * Displays list view title, content and items.
     */
    @Override
    public void display() {
        displayTitle();
        displayContent();
        displayItems();
        System.out.println();
    }

    /**
     * Gets the choice of the view items from the user.
     * @return the choice of the view items from the user.
     */
    @Override
    public String getChoice() {
        String viewItemRange = viewItems.size() > 1 ? "1-" + viewItems.size() :
                (viewItems.size() == 1 ? "1" : null);
        String menuItemRange = menuItems.size() > 1 ?
                "A-" + ((char) ('A' + menuItems.size() - 1)) :
                (menuItems.size() == 1 ? "A" : null);
        while (true) {
            String input = Form.getString(PROMPT + " [ " + (viewItemRange == null ? "" :
                    (viewItemRange + ", ")) + menuItemRange + " ]");
            try {
                int viewItemIndex = Integer.parseInt(input) - 1;
                if (viewItemIndex < viewItems.size())
                    return viewItems.get(viewItemIndex).getValue();
                View.displayError(INVALID_ERROR);
            } catch (NumberFormatException e) {
                if (input.length() == 1) {
                    int menuItemIndex = input.charAt(0) - 'A';
                    if (menuItemIndex < menuItems.size())
                        return menuItems.get(menuItemIndex).getValue();
                    View.displayError(INVALID_ERROR);
                } else
                    View.displayError(UNRECOGNIZED_ERROR);
            }
        }
    }
}
