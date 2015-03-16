package net.wurmevolved.server.game.menu;

import net.wurmevolved.common.constants.Action;

public class MenuFactory {

    public Menu makeMenu(String wiki) {
        return new Menu(wiki);
    }

    public MenuItem makeMenuItem(Action action) {
        return new ActionItem(action);
    }

}
