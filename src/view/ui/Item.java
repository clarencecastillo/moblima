package view.ui;

public interface Item {

    char DELIMITER = '|';
    int RIGHT_PADDING = 1;
    int LABEL_LENGTH = 4;

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
