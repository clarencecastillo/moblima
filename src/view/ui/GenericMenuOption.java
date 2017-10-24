package view.ui;

public class GenericMenuOption implements MenuOption {

    private String description;
    private String value;

    public GenericMenuOption(String description, String value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }
}
