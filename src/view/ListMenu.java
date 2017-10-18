package view;

public class ListMenu extends Menu {

    private View[] items;

    public ListMenu(String title, View[] items, boolean canGoBack) {
        super(title, canGoBack);
        this.items = items;
        this.content = new String[items.length];
    }

    @Override
    public void display() {
        for (int i = 0; i < items.length; i++) {
            View item = items[i];
            System.out.println(Line.format(Line.format(String.valueOf(i + 1), 4),
                                           View.VERTICAL_SEPARATOR, item.content[0]));
            for (String line : item.content)
                System.out.println(Line.format("    ",
                                               View.VERTICAL_SEPARATOR, line));
        }
        if (canGoBack)
            System.out.println(Line.format(Line.format(String.valueOf(0), 4),
                                           View.VERTICAL_SEPARATOR, BACK_OPTION));
        System.out.println(Line.format('-', View.VIEW_WIDTH));
    }

    public void setItems(View[] items) {
        this.items = items;
    }

    public View[] getItems() {
        return items;
    }
}
