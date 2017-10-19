package view;

public interface Item {

    char DELIMITER = '|';
    int LEFT_PADDING = 1;
    int LABEL_LENGTH = 3;

    void display(int index);

    static String prepareLabel(int index) {
        String label = View.line(' ', LEFT_PADDING) + index;
        label += View.line(' ', LABEL_LENGTH - label.length());
        return label;
    }
}
