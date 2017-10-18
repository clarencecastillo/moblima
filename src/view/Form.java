package view;

import exception.InputOutOfBoundsException;
import exception.InputUnrecognisedException;
import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Form extends View {

    private final static String PROMPT_DELIMETER = " > ";
    private final static String DATE_FORMAT = "dd/MM/yyyy HH:mm";

    public Form(String title, String[] content) {
        super(title, content);
    }

    public Form(String title) {
        super(title);
    }

    public Form() {
        super();
    }

    public int getInt(String prompt) throws InputUnrecognisedException {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            throw new InputUnrecognisedException(sc.nextLine());
        }
    }

    public int getInt(String prompt, int min, int max) throws InputOutOfBoundsException {
        int input = getInt(prompt + " [" + min + "-" + max + "] ");
        if (input < min || input > max)
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public int getInt(String prompt, int[] options) throws InputOutOfBoundsException {
        int input = getInt(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public double getDouble(String prompt) throws InputUnrecognisedException {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            throw new InputUnrecognisedException(sc.nextLine());
        }
    }

    public double getDouble(String prompt, double min, double max)
        throws InputOutOfBoundsException {
        double input = getDouble(prompt + " [" + min + "-" + max + "] ");
        if (input < min || input > max)
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public double getDouble(String prompt, double[] options) throws InputOutOfBoundsException {
        double input = getDouble(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public String getCensoredString(String prompt, int minLength, int maxLength)
        throws InputOutOfBoundsException {
        String input = getCensoredString(prompt + " [" + minLength + "-" + maxLength + "] ");
        int inputLength = input.length();
        if (inputLength< minLength || inputLength > maxLength)
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public String getCensoredString(String prompt) {
        Console console = System.console();
        return new String(console.readPassword(prompt + PROMPT_DELIMETER));
    }

    public String getString(String prompt) {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getString(String prompt, String[] options) throws InputOutOfBoundsException {
        String input = getString(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public char getChar(String prompt) {
        System.out.print(prompt + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        return sc.next().charAt(0);
    }

    public char getChar(String prompt, char[] options) throws InputOutOfBoundsException {
        char input = getChar(prompt);
        if (!Arrays.asList(options).contains(input))
            throw new InputOutOfBoundsException(input);
        return input;
    }

    public Date getDate(String prompt, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        System.out.print(prompt + " [" + format + "] " + PROMPT_DELIMETER);
        Scanner sc = new Scanner(System.in);
        return dateFormat.parse(sc.nextLine());
    }

    public Date getDate(String prompt) throws ParseException {
        return getDate(prompt, DATE_FORMAT);
    }
}
