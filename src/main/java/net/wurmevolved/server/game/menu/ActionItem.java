package net.wurmevolved.server.game.menu;

import net.wurmevolved.common.constants.ActionType;

import java.util.HashMap;
import java.util.Map;

public class ActionItem extends MenuItem {

    private static final Map<ActionType, ActionItem> items = new HashMap<>();
    private ActionType actionType;

    public ActionItem(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public short getId() {
        return actionType.getId();
    }

    @Override
    public String getCaption() {
        return actionType.getName();
    }

    public static ActionItem get(ActionType actionType) {
        ActionItem item = items.get(actionType);
        if (item == null) {
            items.put(actionType, item = new ActionItem(actionType));
        }
        return item;
    }

}
