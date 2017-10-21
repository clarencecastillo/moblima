package view.ui;

public class MenuItem implements Item {

    private String description;
    private String value;

    public MenuItem(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void display(int index) {
        System.out.println(Item.prepareLabel(index) + DELIMITER + " " + description);
    }

    public void display(char index) {
        System.out.println(Item.prepareLabel(index) + DELIMITER + " " + description);
    }

    @Override
    public String getValue() {
        return value;
    }
}
