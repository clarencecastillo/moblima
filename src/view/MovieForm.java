package view;

public class MovieForm extends Form {

    public MovieForm() {
        super("Create Movie");
    }

    @Override
    public void display() {
        System.out.println(title);
        System.out.println("------------------------");

    }
}
