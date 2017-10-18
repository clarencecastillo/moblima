package view;

import util.ConsoleColor;

public class Line {

    public static final String CONSOLE_COLOR_REGEX = "\u001B\\[[;\\d]*m";

    public static String format(String line, ConsoleColor color) {
        return color.code + line + ConsoleColor.RESET.code;
    }

    public static String format(String left, String right) {
        int leftLength = left.replaceAll(CONSOLE_COLOR_REGEX, "").length();
        int rightLength = right.replaceAll(CONSOLE_COLOR_REGEX, "").length();
        String space = format(' ', View.VIEW_WIDTH - leftLength - rightLength);
        return left + space + right;
    }

    public static String format(Character character, int length) {
        String line = String.format("%0" + length + "d", 0);
        return line.replace("0", character.toString());
    }

    public static String format(String label, String separator, String value) {
        return label + separator + " " + value;
    }

    public static String format(String line, int padding) {
        int lineLength = line.replaceAll(CONSOLE_COLOR_REGEX, "").length();
        String space = format(' ', padding - lineLength);
        return line + space;
    }

    public static String wrap(String text) {
        return wrap(text, View.VIEW_WIDTH);
    }

    public static String wrap(String text, int length) {
        String result = "";
        int lastdelimPos = 0;
        for (String token : text.split(" ", -1)) {
            if (result.length() - lastdelimPos + token.length() > length) {
                result = result + "\n" + token;
                lastdelimPos = result.length() + 1;
            }
            else {
                result += (result.isEmpty() ? "" : " ") + token;
            }
        }
        return result;
    }
}
