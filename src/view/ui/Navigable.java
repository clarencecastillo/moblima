package view.ui;

public interface Navigable {

    void onLoad(NavigationIntent intent, String... args);
    void onEnter();
}
