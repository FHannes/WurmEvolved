package net.wurmevolved.server.game.logic;

import net.wurmevolved.common.constants.ItemType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.ItemDAO;
import net.wurmevolved.server.game.data.factory.ItemFactory;
import net.wurmevolved.server.game.net.packets.server.AddItemPacket;

import java.util.LinkedList;
import java.util.Queue;

public class ItemHandler {

    private World world;
    private Player player;

    private ItemDAO dao;

    public ItemHandler(World world, Player player) {
        this.world = world;
        this.player = player;

        dao = (ItemDAO) DB.getInstance().getDAO("itemDAO");
    }

    public void sendRoot(AbstractItem root) {
        Queue<AbstractItem> itemQueue = new LinkedList<>();
        itemQueue.add(root);
        while (!itemQueue.isEmpty()) {
            AbstractItem item = itemQueue.poll();
            player.send(new AddItemPacket(
                    -1L, item.getParent() == null ? 0L : item.getParent().getId(), item.getId(), item.getIcon(),
                    item.getItemName(), item.getName(), item.getQuality(), item.getDamage(), item.calculateWeight(),
                    null, -1, null, item.getFlags(), item.getMaterial(), item.getTemperature(), item.getRarity(),
                    item.getBodyType()));
            itemQueue.addAll(item.getItems());
        }
    }

    public void init() {
        if (player.getInventory().getItems().isEmpty()) {
            player.getInventory().addItem(new ItemFactory().makeItem(ItemType.HATCHET));
            dao.save(player.getInventory());
        }

        sendRoot(player.getBody());
        sendRoot(player.getInventory());
    }

}
