package controller;

import view.View;

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

    public abstract void onLoad(String[] arguments);

    public abstract void setupView();

    public abstract View getView();

    public abstract void onViewDisplay();
}
