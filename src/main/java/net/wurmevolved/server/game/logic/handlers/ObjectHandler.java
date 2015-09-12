package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.ItemDAO;
import net.wurmevolved.server.game.data.db.dao.TileDAO;
import net.wurmevolved.server.game.net.packets.server.AddObjectPacket;

public class ObjectHandler {

    private World world;

    private ItemDAO itemDAO;
    private TileDAO tileDAO;

    public ObjectHandler(World world) {
        this.world = world;

        itemDAO = (ItemDAO) DB.getInstance().getDAO("itemDAO");
        tileDAO = (TileDAO) DB.getInstance().getDAO("tileDAO");
    }

    public void drop(AbstractItem item, Position position) {
        assert item.getPos().getLayer().equals(Layer.NONE);

        item.setPos(position);
        Tile tile = world.getTerrainBuffer().getTile(position);
        tile.addItem(item);

        for (Player player : world.getPlayers().getContainedLocal(position)) {
            player.addLocal(item);
            player.send(new AddObjectPacket(item.getId(), item.getModel(), item.getPos(), item.getItemName(),
                    item.getMaterial()));
        }

        itemDAO.save(item);
        tileDAO.save(tile);
    }

}
