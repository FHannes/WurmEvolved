package com.wurmemu.server.game.data.factory;

import com.wurmemu.common.constants.EntityType;
import com.wurmemu.common.constants.ItemType;
import com.wurmemu.server.game.data.AbstractItem;
import com.wurmemu.server.game.data.Item;
import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.ItemDAO;

public class ItemFactory {

    private ItemDAO dao;
    private IDFactory idFactory;

    public ItemFactory() {
        dao = (ItemDAO) DB.getInstance().getDAO("itemDAO");
        idFactory = new IDFactory("item", EntityType.ITEM);
    }

    private Position makeEmptyPos() {
        Position pos = new Position();
        pos.setX(0);
        pos.setY(0);
        pos.setZ(0);
        pos.setRot(0);
        pos.setLayer((byte) 0);
        return pos;
    }

    public AbstractItem makeInventory() {
        Item item = new Item();
        item.setId(idFactory.makeID());
        item.setPos(makeEmptyPos());
        item.setWeight(0);
        item.setType(ItemType.INVENTORY);
        dao.save(item);
        return item;
    }

}
