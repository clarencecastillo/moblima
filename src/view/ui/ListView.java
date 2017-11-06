package view.ui;

import java.util.*;

/**
 * Represents a list view.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public abstract class ListView extends MenuView implements Navigable {

    // TODO Javadoc
    private LinkedHashMap<String, ArrayList<ViewItem>> groupings;
    private ArrayList<ViewItem> noGrouping;
    private ArrayList<ViewItem> viewItems;

    /**
     * Creates a list view with the given navigation.
     * @param navigation
     */
    public ListView(Navigation navigation) {
        super(navigation);
    }

    /**
     * Sets the view items for this menu view.
     * @param viewItems The view items for this menu view.
     */
    protected void setViewItems(List<ViewItem> viewItems) {

        groupings = new LinkedHashMap<>();
        noGrouping = new ArrayList<>();
        this.viewItems = new ArrayList<>();

        for (ViewItem viewItem : viewItems) {
            String groupingLabel = viewItem.getGroupingLabel();
            if (groupingLabel != null)
                if (groupings.containsKey(groupingLabel))
                    groupings.get(groupingLabel).add(viewItem);
                else
                    groupings.put(groupingLabel, new ArrayList<>(Arrays.asList(viewItem)));
            else
                noGrouping.add(viewItem);
        }

        for (String groupingLabel : groupings.keySet()) {
            ArrayList<ViewItem> groupingItems = groupings.get(groupingLabel);
            Collections.sort(groupingItems);
            this.viewItems.addAll(groupingItems);
        }
        Collections.sort(noGrouping);
        this.viewItems.addAll(noGrouping);
    }

    /**
     * Displays view items.
     */
    @Override
    public void displayItems() {

        if (viewItems.size() == 0)
            System.out.println("No items to show.");
        else {
            int nextLabel = 1;

            for (String groupingLabel : groupings.keySet()) {
                System.out.println(groupingLabel.toUpperCase());
                System.out.println(View.line('-', groupingLabel.length()));
                for (ViewItem viewItem : groupings.get(groupingLabel)) {
                    viewItem.display(nextLabel++);
                    System.out.println();
                }
                System.out.println();
            }

            for (ViewItem viewItem : noGrouping) {
                viewItem.display(nextLabel++);
                System.out.println();
            }
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
