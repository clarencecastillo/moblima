package view;

import util.ConsoleColor;

public class View implements Displayable {

    public static final int VIEW_WIDTH = 100;
    public final static String VERTICAL_SEPARATOR = "|";
    public final static String HORIZONTAL_SEPARATOR = "-";

    public final static ConsoleColor INFO = ConsoleColor.CYAN;
    public final static ConsoleColor WARN = ConsoleColor.YELLOW;
    public final static ConsoleColor ERROR = ConsoleColor.RED;
    public final static ConsoleColor SUCCESS = ConsoleColor.GREEN;

    protected String title;
    protected String[] content;

    public View(String title, String[] content) {
        this.title = title.toUpperCase();
        this.content = content;
    }

    public View(String title) {
        this.title = title.toUpperCase();
        this.content = null;
    }

    public View() {
        this.title = null;
        this.content = null;
    }

    public void displayHeader() {
        if (title == null)
            return;
        System.out.println(Line.format(HORIZONTAL_SEPARATOR.charAt(0), VIEW_WIDTH));
        System.out.println(title);
        System.out.println(Line.format(HORIZONTAL_SEPARATOR.charAt(0), VIEW_WIDTH));
    }

    public void display() {
        if (content == null || content.length == 0)
            return;
        for (String line : content)
            System.out.println(Line.wrap(line, VIEW_WIDTH));
        System.out.println();
    }

    public void displayInformation(String line) {
        System.out.println(Line.format(line, INFO));
    }

    public void displayWarning(String line) {
        System.out.println(Line.format(line, WARN));
    }

    public void displayError(String line) {
        System.out.println(Line.format(line, ERROR));
    }

    public void displaySuccess(String line) {
        System.out.println(Line.format(line, SUCCESS));
    }

    public String getTitle() {
        return title;
    }

    public String[] getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String[] content) {
        this.content = content;
    }
}
