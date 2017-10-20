package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class View {

    public final static int VIEW_WIDTH = 100;

    public final static String DASH_LINE = line('-', VIEW_WIDTH);
    public final static String SPACE_LINE = line(' ', VIEW_WIDTH);

    public final static ConsoleColor INFO = ConsoleColor.CYAN;
    public final static ConsoleColor WARN = ConsoleColor.YELLOW;
    public final static ConsoleColor ERROR = ConsoleColor.RED;
    public final static ConsoleColor SUCCESS = ConsoleColor.GREEN;

    protected String title;
    protected ArrayList<String> content = new ArrayList<>();

    protected void displayHeader() {
        System.out.println(DASH_LINE);
        System.out.println(title);
        System.out.println(DASH_LINE);
    }

    protected void displayContent() {
        for (String string: content)
            for (String stringLine : wrap(string, VIEW_WIDTH))
                System.out.println(stringLine);
        System.out.println();
    }

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

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setContent(String[] content) {
        this.content.clear();
        this.content.addAll(Arrays.asList(content));
    }

    protected void setContent(String content) {
        this.content.clear();
        this.content.add(content);
    }
}
