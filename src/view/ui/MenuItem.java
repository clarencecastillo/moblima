package view.ui;

/**
 * Represents a menu item to be displayed in the view.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class MenuItem implements Item {

    /**
     * The description of this menu item.
     */
    private String description;

    /**
     * The value of this menu item which is the string to be displayed.
     */
    private String value;

    /**
     * Created a menu item with the given description and value.
     * @param description The description of this menu item.
     * @param value The value of this menu item which is the string to be displayed.
     */
    public MenuItem(String description, String value) {
        this.description = description;
        this.value = value;
    }

    /**
     * Gets the description of this menu item.
     * @return the description of this menu item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the description of this menu item.
     * @param description the new description of this menu item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Display this menu item with the given integer index.
     * @param index the integer index to be displayed in front of this menu option.
     */
    @Override
    public void display(int index) {
        System.out.println(Item.prepareLabel(index) + DELIMITER + " " + description);
    }

    /**
     * Display this menu item with the given character index.
     * @param index the character index to be displayed in front of this menu option.
     */
    public void display(char index) {
        System.out.println(Item.prepareLabel(index) + DELIMITER + " " + description);
    }

    /**
     * Gets the value of this menu option.
     * @return the value of this menu option.
     */
    @Override
    public String getValue() {
        return value;
    }
}
