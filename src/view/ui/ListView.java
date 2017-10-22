package view.ui;

import exception.InputOutOfBoundsException;
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
    public String getChoiceIgnoreMismatch() {
        while(true)
            try {
                String viewItemRange = viewItems.size() > 1 ? "1-" + viewItems.size() :
                                       (viewItems.size() == 1 ? "1" : "");
                String menuItemRange = menuItems.size() > 1 ?
                                       "A-" + ((char) ('A' + menuItems.size() - 1)) :
                                       (menuItems.size() == 1 ? "A" : "");
                String input = Form.getString(INPUT_PROMPT + " [" +
                                              String.join(", ",
                                                          viewItemRange, menuItemRange) + "]");
                try {
                    int viewItemIndex = Integer.parseInt(input) - 1;
                    if (viewItemIndex >= viewItems.size())
                        throw new InputOutOfBoundsException(viewItemIndex);
                    return viewItems.get(viewItemIndex).getValue();
                } catch (NumberFormatException e) {
                    if (input.length() != 1)
                        throw new InputMismatchException(input);
                    else {
                        int menuItemIndex = input.charAt(0) - 'A';
                        if (menuItemIndex >= menuItems.size())
                            throw new InputOutOfBoundsException(menuItemIndex);
                        return menuItems.get(menuItemIndex).getValue();
                    }
                }
            } catch (InputOutOfBoundsException e) {
                View.displayError(INVALID_ERROR);
            }
    }
}
