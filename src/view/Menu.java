package view;

import java.util.Hashtable;
import java.util.Scanner;

public class Menu extends Form {

    private Hashtable<Character, String> options;
    private String prompt;

    public Menu(String title, Hashtable<Character, String> options, String prompt) {
        super(title);
        this.options = options;
        this.prompt = prompt;
    }

    @Override
    public void display() {
        System.out.println(title);
        System.out.println("------------------------");
        for (Character key: options.keySet())
            System.out.println(" " + key + ": " + options.get(key));
    }

    public char getOption() {
        System.out.println("------------------------");
        char option = getChar(prompt);
        if (!options.keySet().contains(option)) {
            System.out.println("Input not recognised!");
            return getOption();
        }
        return option;
    }
}