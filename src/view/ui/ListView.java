package view.ui;

import exception.InputOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;

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
        int itemIndex = 1;
        for (int i = 0; i < viewItems.size(); i++, itemIndex++)
            viewItems.get(i).display(itemIndex);
        if (menuItems.size() > 0) {
            System.out.println();
            for (int i = 0; i < menuItems.size(); i++, itemIndex++)
                menuItems.get(i).display(itemIndex);
        }
        System.out.println();
    }

    @Override
    public void display() {
        displayTitle();
        displayContent();
        displayItems();
    }

    @Override
    public String getChoiceIgnoreMismatch() {
        int totalItems = menuItems.size() + viewItems.size();
        while(true)
            try {
                int index = Form.getInt(INPUT_PROMPT, 1, totalItems) - 1;
                return index < viewItems.size() ?
                       viewItems.get(index).getValue() :
                       menuItems.get(index - viewItems.size()).getValue();
            } catch (InputOutOfBoundsException e) {
                System.out.println(INVALID_ERROR);
            }
    }
}
