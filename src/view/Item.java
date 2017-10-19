package view;

public interface Item {

    char DELIMITER = '|';
    int LEFT_PADDING = 1;
    int LABEL_LENGTH = 3;

    String getLabel();
    void setLabel(String label);
    void display();

}
