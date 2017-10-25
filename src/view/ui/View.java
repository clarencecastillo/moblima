package view.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class View {

    public final static int VIEW_WIDTH = 100;
    public static final String DATE_DISPLAY_FORMAT = "EEEEE, dd MMMMM YYYY";

    public final static String DASH_LINE = line('-', VIEW_WIDTH);
    public final static String SPACE_LINE = line(' ', VIEW_WIDTH);

    public final static ConsoleColor INFO = ConsoleColor.CYAN;
    public final static ConsoleColor WARN = ConsoleColor.YELLOW;
    public final static ConsoleColor ERROR = ConsoleColor.RED;
    public final static ConsoleColor SUCCESS = ConsoleColor.GREEN;

    protected String title;
    protected ArrayList<String> content = new ArrayList<>();

    public static void displayColored(String message, ConsoleColor color) {
        System.out.println(color.code + message + ConsoleColor.RESET.code);
    }

    public static void displayInformation(String message) {
        displayColored(message, INFO);
        System.out.println();
    }

    public static void displayWarning(String message) {
        displayColored(message, WARN);
        System.out.println();
    }

    public static void displayError(String message) {
        displayColored(message, ERROR);
        System.out.println();
    }

    public static void displaySuccess(String message) {
        displayColored(message, SUCCESS);
        System.out.println();
    }

    protected static String line(char character, int length) {
        String line = String.format("%0" + length + "d", 0);
        return line.replace("0", String.valueOf(character));
    }

    protected static String[] wrap(String text, int length) {
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
        return content.toArray(new String[content.size()]);
    }

    public void setContent(String... content) {
        this.content.clear();
        this.content.addAll(Arrays.asList(content));
    }

    public void displayTitle() {
        System.out.println(DASH_LINE);
        System.out.println(title);
        System.out.println(DASH_LINE);
    }

    public void displayContent() {
        for (String line : content)
            for (String stringLine : wrap(line, VIEW_WIDTH))
                System.out.println(stringLine);
        System.out.println();
    }

    public void display() {
        displayTitle();
        displayContent();
    }

    public String flatten(String titleDelimiter, String contentDelimiter) {
        return title + titleDelimiter + String.join(contentDelimiter, content);
    }
}
