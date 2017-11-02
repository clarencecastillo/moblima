package view.ui;

import java.util.List;

/**
 * Represents a item in a view.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class ViewItem extends View implements Item {

    /**
     * The string content of this view item.
     */
    public String value;

    /**
     * Creates a view item with the given title, string content and description.
     * @param title The title of this view item.
     * @param value The string value of this view item.
     * @param content The content of this view item.
     */
    public ViewItem(String title, String content, String value) {
        this.title = title;
        this.content.add(content);
        this.value = value;
    }

    /**
     * Creates a view item with the given title, string content and description.
     * @param title The title of this view item.
     * @param value The string value of this view item.
     * @param content The content of this view item.
     */
    public ViewItem(String title, List<String> content, String value) {
        this.title = title;
        this.content.addAll(content);
        this.value = value;
    }

    /**
     * Creates a view item with the given string content.
     * @param value The string content of this view item.
     */
    public ViewItem(String value) {
        this.value = value;
    }

    /**
     * Creates a view item with the given view and string content.
     * @param view The view of this view item.
     * @param value The string content of this view item.
     */
    public ViewItem(View view, String value) {
        this.title = view.title;
        this.content = view.content;
        this.value = value;
    }

    /**
     * Displays this view item with the given index.
     * @param index the index to be displayed.
     */
    @Override
    public void display(int index) {
        int maxLength = VIEW_WIDTH - LABEL_LENGTH - 2;
        if (title != null)
            System.out.println(Item.prepareLabel(index) + DELIMITER + " " + title);
        if (content.size() != 0) {
            System.out.println(line(' ', LABEL_LENGTH) + DELIMITER + " "
                    + line('-', maxLength));
            for (String string : content)
                for (String stringLine : wrap(string, maxLength))
                    System.out.println(line(' ', LABEL_LENGTH) + DELIMITER + " " + stringLine);
        }
    }

    /**
     * Gets the string content of this view item.
     * @return the string content of this view item.
     */
    @Override
    public String getValue() {
        return value;
    }
}
