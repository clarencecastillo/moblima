package controller;

public abstract class Controller {

    protected Navigation navigation;


    public void init(Navigation navigation) {
        if (initialised)
            return;
        initialised = true;
        this.navigation = navigation;
        setupView();
    }

    public abstract void setupView();
    
    public abstract void onEnter(String[] arguments);
}
