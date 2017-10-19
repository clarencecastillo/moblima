package view;

public class MenuItem implements Item {

    public static String BACK_DESCRIPTION = "Go Back";
    public static int BACK_LABEL = 0;
    public static MenuItem BACK = new MenuItem(BACK_LABEL, BACK_DESCRIPTION);

    private String label;
    private String description;

    public MenuItem(int numericLabel, String description) {
        this(String.valueOf(numericLabel), description);
    }

    public MenuItem(String label, String description) {
        this.label = View.line(' ', LEFT_PADDING);
        this.label += label;
        this.label += View.line(' ', LABEL_LENGTH - this.label.length());
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void display() {
        System.out.println(label + DELIMITER + " " + description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
