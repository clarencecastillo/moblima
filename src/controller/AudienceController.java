package controller;

import view.View;

public class AudienceController extends Controller {

    private static AudienceController instance = new AudienceController();

    private AudienceController() { }

    public static AudienceController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {

    }

    @Override
    public void setupView() {

    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void onViewDisplay() {

    }
}
