package net.wurmevolved.server.game.data.factory;

import net.wurmevolved.common.constants.EntityType;
import net.wurmevolved.common.constants.ItemType;
import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.common.constants.Rarity;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Item;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.ItemDAO;

import java.util.LinkedList;
import java.util.List;

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
        pos.setLayer(Layer.NONE);
        return pos;
    }

    public Item createItem(ItemType type, float quality) {
        Item item = new Item();
        item.setId(idFactory.makeID());
        item.setPos(makeEmptyPos());
        item.setQuality(quality);
        item.setDamage(0);
        item.setType(type);
        item.setWeight(item.getType().getWeight());
        item.setMaterial(item.getType().getMaterial());
        item.setTemperature((byte) 0);
        item.setRarity(Rarity.NONE);
        return item;
    }

    public Item makeBody() {
        List<AbstractItem> items = new LinkedList<>();

        Item body = createItem(ItemType.BODY, 50);
        items.add(body);
        Item head = createItem(ItemType.HEAD, 50);
        items.add(head);
        Item neck = createItem(ItemType.NECK, 50);
        items.add(neck);
        Item face = createItem(ItemType.FACE, 50);
        items.add(face);
        Item torso = createItem(ItemType.TORSO, 50);
        items.add(torso);
        Item cape = createItem(ItemType.CAPE, 50);
        items.add(cape);
        Item leftShoulder = createItem(ItemType.LEFT_SHOULDER, 50);
        items.add(leftShoulder);
        Item rightShoulder = createItem(ItemType.RIGHT_SHOULDER, 50);
        items.add(rightShoulder);
        Item back = createItem(ItemType.BACK, 50);
        items.add(back);
        Item tabard = createItem(ItemType.TABARD, 50);
        items.add(tabard);
        Item leftArm = createItem(ItemType.LEFT_ARM, 50);
        items.add(leftArm);
        Item shieldSlot = createItem(ItemType.SHIELD_SLOT, 50);
        items.add(shieldSlot);
        Item leftHand = createItem(ItemType.LEFT_HAND, 50);
        items.add(leftHand);
        Item leftRing = createItem(ItemType.LEFT_RING, 50);
        items.add(leftRing);
        Item leftHeldItem = createItem(ItemType.LEFT_HELD_ITEM, 50);
        leftHeldItem.setName("off-hand weapon");
        items.add(leftHeldItem);
        Item rightArm = createItem(ItemType.RIGHT_ARM, 50);
        items.add(rightArm);
        Item rightHand = createItem(ItemType.RIGHT_HAND, 50);
        items.add(rightHand);
        Item rightRing = createItem(ItemType.RIGHT_RING, 50);
        items.add(rightRing);
        Item rightHeldItem = createItem(ItemType.RIGHT_HELD_ITEM, 50);
        rightHeldItem.setName("main weapon");
        items.add(rightHeldItem);
        Item legs = createItem(ItemType.LEGS, 50);
        items.add(legs);
        Item belt = createItem(ItemType.BELT, 50);
        items.add(belt);
        Item hipSlot = createItem(ItemType.HIP_SLOT, 50);
        items.add(hipSlot);
        Item leftFoot = createItem(ItemType.LEFT_FOOT, 50);
        items.add(leftFoot);
        Item rightFoot = createItem(ItemType.RIGHT_FOOT, 50);
        items.add(rightFoot);

        dao.save(items);

        body.addItem(head);
        head.addItem(neck);
        head.addItem(face);
        body.addItem(torso);
        torso.addItem(cape);
        torso.addItem(leftShoulder);
        torso.addItem(rightShoulder);
        torso.addItem(back);
        torso.addItem(tabard);
        torso.addItem(leftArm);
        leftArm.addItem(shieldSlot);
        leftArm.addItem(leftHand);
        leftHand.addItem(leftRing);
        leftHand.addItem(leftHeldItem);
        torso.addItem(rightArm);
        rightArm.addItem(rightHand);
        rightHand.addItem(rightRing);
        rightHand.addItem(rightHeldItem);
        torso.addItem(legs);
        legs.addItem(belt);
        legs.addItem(hipSlot);
        legs.addItem(leftFoot);
        legs.addItem(rightFoot);

        dao.save(body);
        return body;
    }

    public Item makeInventory() {
        return makeItem(ItemType.INVENTORY);
    }

    public Item makeItem(ItemType type, float quality) {
        Item item = createItem(type, quality);
        dao.save(item);
        return item;
    }

    public Item makeItem(ItemType type) {
        return makeItem(type, 100);
    }

}
