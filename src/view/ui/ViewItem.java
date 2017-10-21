package view.ui;

public class ViewItem extends View implements Item {

    public String value;

    public ViewItem(String value) {
        this.value = value;
    }

    @Override
    public void display(int index) {
        int maxLength = VIEW_WIDTH - LABEL_LENGTH - 2;
        if (title != null)
            System.out.println(Item.prepareLabel(index) + DELIMITER + " " + title);
        if (content.size() != 0) {
            System.out.println(line(' ', LABEL_LENGTH) + DELIMITER + " "
                               + line('-', maxLength));
            for (String string: content)
                for (String stringLine : wrap(string, maxLength))
                    System.out.println(line(' ', LABEL_LENGTH) + DELIMITER + " " + stringLine);
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
