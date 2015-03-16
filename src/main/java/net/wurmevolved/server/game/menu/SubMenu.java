package net.wurmevolved.server.game.menu;

import java.util.LinkedList;
import java.util.List;

public class SubMenu extends MenuItem {

    private String caption;
    private List<MenuItem> items = new LinkedList<>();

    public SubMenu(String caption) {
        this.caption = caption;
    }

    @Override
    public short getId() {
        return (short) -items.size();
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void buildMenu(List<MenuItem> menu) {
        if (!(this instanceof Menu)) {
            menu.add(this);
        }
        for (MenuItem item : items) {
            item.buildMenu(menu);
        }
    }

    public void add(MenuItem item) {
        items.add(item);
    }

}
