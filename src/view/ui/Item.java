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
     * The icon of delimiter.
     */
    char DELIMITER = '|';

    /**
     * The number of right padding.
     */
    int RIGHT_PADDING = 1;
    /**
     * The number of label length.
     */
    int LABEL_LENGTH = 4;

    /**
     * Gets the
     * @param index
     * @return
     */
    static String prepareLabel(int index) {
        String label = index + View.line(' ', RIGHT_PADDING);
        label = View.line(' ', LABEL_LENGTH - label.length()) + label;
        return label;
    }

    static String prepareLabel(char index) {
        String label = index + View.line(' ', RIGHT_PADDING);
        label = View.line(' ', LABEL_LENGTH - label.length()) + label;
        return label;
    }

    void display(int index);

    String getValue();
}
