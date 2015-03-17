package net.wurmevolved.server.game.data.factory;

import net.wurmevolved.common.constants.EntityType;
import net.wurmevolved.common.constants.ItemType;
import net.wurmevolved.common.constants.Rarity;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Item;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.ItemDAO;

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
        item.setQuality(100);
        item.setDamage(0);
        item.setType(ItemType.INVENTORY);
        item.setWeight(item.getType().getWeight());
        item.setName("");
        item.setMaterial(item.getType().getMaterial());
        item.setTemperature((byte) 0);
        item.setRarity(Rarity.NONE);
        dao.save(item);
        return item;
    }

    public AbstractItem makeItem(ItemType type) {
        Item item = new Item();
        item.setId(idFactory.makeID());
        item.setPos(makeEmptyPos());
        item.setQuality(100);
        item.setDamage(0);
        item.setType(type);
        item.setWeight(item.getType().getWeight());
        item.setName("");
        item.setMaterial(item.getType().getMaterial());
        item.setTemperature((byte) 0);
        item.setRarity(Rarity.NONE);
        dao.save(item);
        return item;
    }

}
