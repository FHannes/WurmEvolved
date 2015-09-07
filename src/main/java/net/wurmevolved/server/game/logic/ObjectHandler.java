package net.wurmevolved.server.game.logic;

import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.ItemDAO;
import net.wurmevolved.server.game.data.db.dao.TileDAO;
import net.wurmevolved.server.game.logic.observers.impl.LocalObjectObserver;

public class ObjectHandler {

    private World world;

    private ItemDAO dao;
    private TileDAO tileDAO;

    public ObjectHandler(World world) {
        this.world = world;

        dao = (ItemDAO) DB.getInstance().getDAO("itemDAO");
        tileDAO = (TileDAO) DB.getInstance().getDAO("tileDAO");
    }

    public void drop(AbstractItem item, Position position) {
        assert !item.getPos().getLayer().equals(Layer.NONE);

        // The order of execution is important as setting the position will trigger a method in the observer
        for (Player player : world.getPlayers().getContainedLocal(position)) {
            item.getObservers().add(new LocalObjectObserver(player));
        }
        item.setPos(position);
        Tile tile = world.getTerrainBuffer().getTile(position);
        tile.addItem(item);
        dao.save(item);
        tileDAO.save(tile);
    }

}
