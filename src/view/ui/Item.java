package view.ui;

/**
 * Represents a standard interface that must be implemented by an item on the interface.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */
public interface Item {

    /**
     * The icon of delimiter is "|".
     */
    char DELIMITER = '|';

    /**
     * The number of right padding is 4.
     */
    int RIGHT_PADDING = 1;

    /**
     * The number of label length is 4.
     */
    int LABEL_LENGTH = 4;

    /**
     * Gets the prepared label of the given integer index with the preset padding.
     * @param index The integer index of the prepared lable to be returned.
     * @return the prepared label of the given index with the preset padding as a string.
     */
    static String prepareLabel(int index) {
        String label = index + View.line(' ', RIGHT_PADDING);
        label = View.line(' ', LABEL_LENGTH - label.length()) + label;
        return label;
    }

    /**
     * Gets the prepared label of the given character index with the preset padding.
     * @param index The character index of the prepared lable to be returned.
     * @return the prepared label of the given index with the preset padding as a string.
     */
    static String prepareLabel(char index) {
        String label = index + View.line(' ', RIGHT_PADDING);
        label = View.line(' ', LABEL_LENGTH - label.length()) + label;
        return label;
    }

    /**
     * Displays the given index.
     * @param index the index to be displayed.
     */
    void display(int index);

    /**
     * Gets the value of this item.
     * @return the value of this item.
     */
    String getValue();
}
