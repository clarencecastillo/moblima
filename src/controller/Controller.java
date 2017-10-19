package controller;

public abstract class Controller {

    protected Navigation navigation;
    protected boolean initialised;

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
