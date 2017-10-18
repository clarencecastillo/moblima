package controller;

import view.Menu;
import view.MenuOption;
import view.View;

public class RankingMenuController extends Controller {

    private static RankingMenuController instance = new RankingMenuController();

    private View rankingView;
    private Menu rankingMenu;


    private RankingMenuController() {}

    public static RankingMenuController getInstance() {
        return instance;
    }

    @Override
    public void onLoad(String[] arguments) {}

    @Override
    public void setupView() {
        rankingView = new View("Movie Ranking Option");
        rankingMenu = new Menu();
        rankingMenu.setContent(Menu.getDescriptions(rankingMenuOption.values()));
    }

    @Override
    public View getView() {
        return rankingView;
    }

    @Override
    public void onViewDisplay() {

        rankingMenu.display();

        rankingMenuOption userChoice = rankingMenuOption.values()[rankingMenu.getChoice()];

        switch (userChoice) {
            case RANK_BY_TICKET_SALES:
                break;
            case RANK_BY_RATINGS:
                break;
        }
    }

    private enum rankingMenuOption implements MenuOption {

        RANK_BY_TICKET_SALES("Rank by ticket sales"),
        RANK_BY_RATINGS("Rank by ratings");

        private String description;
        rankingMenuOption(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }
}


