package view.ui;

public interface Navigable {

    void onLoad(AccessLevel accessLevel, Intent intent, String... args);
    void onEnter();
}
