package view;


import java.util.Scanner;

public abstract class Form implements Displayable {

    private Scanner sc = new Scanner(System.in);
    protected String title;

    public Form(String title) {
        this.title = title;
    }

    protected char getChar(String prompt) {
        System.out.println(prompt + ": ");
        return sc.next().charAt(0);
    }

    protected int getInt(String prompt) {
        System.out.println(prompt + ": ");
        return sc.nextInt();
    }

    protected String getString(String prompt) {
        System.out.println(prompt + ": ");
        return sc.next();
    }

    public abstract void display();
}
