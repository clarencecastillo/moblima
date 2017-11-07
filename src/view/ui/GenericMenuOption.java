package view.ui;

/**
 * Represents a manually created menu option.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class GenericMenuOption implements MenuOption {

    /**
     * The description of this generic menu option.
     */
    private String description;

    /**
     * The value of this generic menu option.
     */
    private String value;

    /**
     * Creates a generic menu option by the given description and value.
     * @param description The description of this generic menu option.
     * @param value The value of this generic menu option.
     */
    public GenericMenuOption(String description, String value) {
        this.description = description;
        this.value = value;
    }

    /**
     * Gets the description of a given generic menu option.
     * @return the description of this generic menu option.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the value of a given generic menu option.
     * @return the value of this generic menu option.
     */
    public String getValue() {
        return value;
    }
}
