package com.wurmemu.server.game.data.factory;

import com.wurmemu.common.constants.EntityType;
import com.wurmemu.common.constants.ItemType;
import com.wurmemu.server.game.data.AbstractItem;
import com.wurmemu.server.game.data.Item;
import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.ItemDAO;

public class ItemFactory {

    private ItemDAO dao;
    private IDFactory idFactory;

    public ItemFactory() {
        dao = (ItemDAO) DB.getInstance().getDAO("itemDAO");
        idFactory = new IDFactory("item", EntityType.ITEM);
    }

    public AbstractItem makeInventory() {
        Item item = new Item();
        item.setType(ItemType.INVENTORY);
        dao.save(item);
        return item;
    }

}
