package com.wurmemu.server.game.menu;

import com.wurmemu.common.constants.Action;

import java.util.HashMap;
import java.util.Map;

public class ActionItem extends MenuItem {

    private static final Map<Action, ActionItem> items = new HashMap<>();
    private Action action;

    public ActionItem(Action action) {
        this.action = action;
    }

    @Override
    public short getId() {
        return action.getId();
    }

    @Override
    public String getCaption() {
        return action.getName();
    }

    public static ActionItem get(Action action) {
        ActionItem item = items.get(action);
        if (item == null) {
            items.put(action, item = new ActionItem(action));
        }
        return item;
    }

}
