package view;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import view.layout.LayoutColor;
import view.layout.PartitionLayout;
import view.layout.StringLayout;

public class View implements Displayable {

    public final static int VIEW_WIDTH = 100;

    public final static String DASH_LINE = line('-', VIEW_WIDTH);
    public final static String SPACE_LINE = line(' ', VIEW_WIDTH);

    public final static LayoutColor INFO = LayoutColor.CYAN;
    public final static LayoutColor WARN = LayoutColor.YELLOW;
    public final static LayoutColor ERROR = LayoutColor.RED;
    public final static LayoutColor SUCCESS = LayoutColor.GREEN;

    protected String title;
    protected String[] content;

    public void displayHeader() {
        System.out.println(DASH_LINE);
        System.out.println(title);
        System.out.println(DASH_LINE);
    }

    public void display() {
        for (String string: content)
            System.out.println(string);
        System.out.println();
    }

    public void displayInformation(String message) {
        System.out.println(new StringLayout(message, INFO));
        System.out.println();
    }

    public void displayInformation(String leftMessage, String rightMessage) {
        System.out.println(new PartitionLayout(leftMessage, rightMessage, INFO));
        System.out.println();
    }

    public void displayWarning(String message) {
        System.out.println(new StringLayout(message, WARN));
        System.out.println();
    }

    public void displayWarning(String leftMessage, String rightMessage) {
        System.out.println(new PartitionLayout(leftMessage, rightMessage, WARN));
        System.out.println();
    }

    public void displayError(String message) {
        System.out.println(new StringLayout(message, ERROR));
        System.out.println();
    }

    public void displayError(String leftMessage, String rightMessage) {
        System.out.println(new PartitionLayout(leftMessage, rightMessage, ERROR));
        System.out.println();
    }

    public void displaySuccess(String message) {
        System.out.println(new StringLayout(message, SUCCESS));
        System.out.println();
    }

    public void displaySuccess(String leftMessage, String rightMessage) {
        System.out.println(new PartitionLayout(leftMessage, rightMessage, SUCCESS));
        System.out.println();
    }

    public static String line(char character, int length) {
        String line = String.format("%0" + length + "d", 0);
        return line.replace("0", String.valueOf(character));
    }

    public static String[] wrap(String text, int length) {
        ArrayList<String> lines = new ArrayList<>();
        Pattern regex = Pattern.compile(String.format("(.{1,%s}(?:\\s|$))|(.{0,10})", length),
                                        Pattern.DOTALL);
        Matcher regexMatcher = regex.matcher(text);
        while (regexMatcher.find()) {
            String match = regexMatcher.group();
            if (match.length() > 0)
                lines.add(match);
        }
        return lines.toArray(new String[lines.size()]);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }
}
