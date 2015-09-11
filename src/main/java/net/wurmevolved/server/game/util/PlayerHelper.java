package net.wurmevolved.server.game.util;

import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Item;
import net.wurmevolved.server.game.data.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerHelper {

    public static List<AbstractItem> getLocalItems(Player p) {
        List<AbstractItem> items = new ArrayList<>();
        items.addAll(p.getLocal(Item.class));
        // TODO: Add list of custom item class when implemented
        return items;
    }

}
