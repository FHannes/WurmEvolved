package net.wurmevolved.server.game.menu;

import net.wurmevolved.common.constants.ActionType;

public class MenuFactory {

    public Menu makeMenu(String wiki) {
        return new Menu(wiki);
    }

    public MenuItem makeMenuItem(ActionType actionType) {
        return new ActionItem(actionType);
    }

}
