package view.ui;

import exception.InputOutOfBoundsException;
import exception.InputUnrecognisedException;
import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface Form {

    String PROMPT_DELIMETER = " > ";
    String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    static int getInt(String prompt) throws InputUnrecognisedException {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            throw new InputUnrecognisedException(sc.nextLine());
        }
    }

    static int getInt(String prompt, int min, int max) throws InputOutOfBoundsException {
        int input = getInt(prompt + " [" + min + "-" + max + "]");
        if (input < min || input > max)
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static int getInt(String prompt, int[] options) throws InputOutOfBoundsException {
        int input = getInt(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static double getDouble(String prompt) throws InputUnrecognisedException {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            throw new InputUnrecognisedException(sc.nextLine());
        }
    }

    static double getDouble(String prompt, double min, double max)
        throws InputOutOfBoundsException {
        double input = getDouble(prompt + " [" + min + "-" + max + "]");
        if (input < min || input > max)
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static double getDouble(String prompt, double[] options) throws InputOutOfBoundsException {
        double input = getDouble(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static String getCensoredString(String prompt, int minLength, int maxLength)
        throws InputOutOfBoundsException {
        String input = getCensoredString(prompt + " [" + minLength + "-" + maxLength + "]");
        int inputLength = input.length();
        if (inputLength< minLength || inputLength > maxLength)
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static String getCensoredString(String prompt) {
        Console console = System.console();
        return new String(console.readPassword(prompt + PROMPT_DELIMETER));
    }

    static String getString(String prompt) {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    static String getString(String prompt, String[] options) throws InputOutOfBoundsException {
        String input = getString(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static char getChar(String prompt) {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        if (input.length() != 1)
            throw new InputUnrecognisedException(input);
        return input.charAt(0);
    }

    static char getChar(String prompt, char[] options) throws InputOutOfBoundsException {
        char input = getChar(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    static char getChar(String prompt, char min, char max) throws InputOutOfBoundsException {
        char input = getChar(prompt + " [" + min + "-" + max + "]");
        if (input < min || input > max)
            throw new InputOutOfBoundsException(input);
        return input;
    }
    
    static Date getDate(String prompt, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        System.out.print(prompt + " [" + format + "]" + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        return dateFormat.parse(sc.nextLine());
    }

    static Date getDate(String prompt) throws ParseException {
        return getDate(prompt, DATE_FORMAT);
    }

    static void pressAnyKeyToContinue() {
        System.out.print("Press ENTER key to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
