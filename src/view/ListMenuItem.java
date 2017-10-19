package view;

public class ListMenuItem extends View implements Item {

    private String label;

    public ListMenuItem(int numericLabel) {
        this(String.valueOf(numericLabel));
    }

    public ListMenuItem(String label) {
        this.label = line(' ', LEFT_PADDING);
        this.label += label;
        this.label += line(' ', LABEL_LENGTH - this.label.length());
    }

    @Override
    public void displayHeader() {
        System.out.println(label + DELIMITER + " " + title);
    }

    @Override
    public void display() {

        int maxLength = VIEW_WIDTH - LABEL_LENGTH - 2;
        if (title != null)
            displayHeader();
        if (content == null || content.length == 0)
            return;
        for (String string: content)
            for (String stringLine : wrap(string, maxLength))
                System.out.println(line(' ', LABEL_LENGTH) + DELIMITER + " " + stringLine);
        System.out.println();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
