package net.wurmevolved.server.game.logic;

import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.ItemDAO;
import net.wurmevolved.server.game.data.factory.ItemFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemList {

    private Map<Long, AbstractItem> items = new ConcurrentHashMap<>();
    private ItemDAO dao = (ItemDAO) DB.getInstance().getDAO("itemDAO");
    private ItemFactory itemFactory = new ItemFactory();

    public void load() {
        items.clear();
        for (AbstractItem item : dao.listGroundItems()) {
            items.put(item.getId(), item);
        }
    }

    public Collection<AbstractItem> all() {
        return items.values();
    }

    public Set<AbstractItem> getLocal(Position pos) {
        Set<AbstractItem> items = new HashSet<>();
        for (AbstractItem item : this.items.values()) {
            if (item.getPos().maxAxisDistance(pos) <= item.getPos().getLayer().getLocal()) {
                items.add(item);
            }
        }
        return items;
    }

    public void save(AbstractItem item) {
        dao.save(item);
    }

    public AbstractItem get(long id) {
        return items.get(id);
    }

}
