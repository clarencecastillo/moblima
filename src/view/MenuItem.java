package view;

public class MenuItem implements Item {

    private String description;
    private String value;

    public MenuItem(String description, String value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public void display(int index) {
        System.out.println(Item.prepareLabel(index) + DELIMITER + " " + description);
    }

    public String getValue() {
        return value;
    }
}
