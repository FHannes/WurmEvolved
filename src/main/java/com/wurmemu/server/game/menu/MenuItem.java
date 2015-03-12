package com.wurmemu.server.game.menu;

import java.util.List;

public abstract class MenuItem {

    public abstract short getId();
    public abstract String getCaption();
    public void buildMenu(List<MenuItem> menu) {
        menu.add(this);
    }

}
