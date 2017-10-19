package view;

public class ListMenu extends Menu {

    public void displayMenuItems() {
        for (Item listMenuItem : menuItems) {
            System.out.println();
            listMenuItem.display();
        }
        System.out.println(DASH_LINE);
    }

    public void displayMenuItemsWithBack() {
        displayMenuItemsWithBack(MenuItem.BACK_DESCRIPTION);
    }

    public void displayMenuItemsWithBack(String backOption) {
        for (Item listMenuItem : menuItems) {
            System.out.println();
            listMenuItem.display();
        }
        System.out.println();
        new MenuItem(MenuItem.BACK_LABEL, backOption).display();
        System.out.println(DASH_LINE);
    }
}
