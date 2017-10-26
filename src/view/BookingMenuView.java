package view;

import view.ui.AccessLevel;
import view.ui.Intent;
import view.ui.MenuView;
import view.ui.Navigation;

public class BookingMenuView extends MenuView {

    private AccessLevel accessLevel;

    public BookingMenuView(Navigation navigation) {
        super(navigation);
    }

    @Override
    public void onLoad(AccessLevel accessLevel, Intent intent, String... args) {

        this.accessLevel = accessLevel;
//        switch (accessLevel) {
//            case ADMINISTRATOR:
//                setMenuItems(MovieMenuView.MovieMenuOption.values());
//                break;
//            case PUBLIC:
//                setMenuItems(MovieMenuView.MovieMenuOption.VIEW_SHOWTIMES,
//                        MovieMenuView.MovieMenuOption.SEE_REVIEWS);
//                break;
//        }
    }

    @Override
    public void onEnter() {

    }
}
