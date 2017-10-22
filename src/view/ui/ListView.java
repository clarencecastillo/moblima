package view.ui;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public abstract class ListView extends MenuView implements Navigable {

    private ArrayList<ViewItem> viewItems = new ArrayList<>();

    public ListView(Navigation navigation) {
        super(navigation);
    }

    protected ArrayList<ViewItem> getViewItems() {
        return viewItems;
    }

    protected void setViewItems(ViewItem[] viewItems) {
        this.viewItems.clear();
        this.viewItems.addAll(Arrays.asList(viewItems));
    }

    @Override
    public void displayItems() {
        for (int i = 0; i < viewItems.size(); i++) {
            viewItems.get(i).display(i + 1);
            System.out.println();
        }
        if (menuItems.size() > 0) {
            System.out.println();
            super.displayItems();
        }
    }

    @Override
    public void display() {
        displayTitle();
        displayContent();
        displayItems();
        System.out.println();
    }

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
