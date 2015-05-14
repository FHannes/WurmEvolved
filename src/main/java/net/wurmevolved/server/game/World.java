package net.wurmevolved.server.game;

import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.logic.ItemList;
import net.wurmevolved.server.game.logic.PlayerList;
import net.wurmevolved.server.game.map.TerrainBuffer;

public class World {

    private PlayerList players;
    private TerrainBuffer terrainBuffer;
    private ItemList items;

    public World() {
        players = new PlayerList();
        terrainBuffer = new TerrainBuffer();
        items = new ItemList();
    }

    public PlayerList getPlayers() {
        return players;
    }

    public TerrainBuffer getTerrainBuffer() {
        return terrainBuffer;
    }

    public ItemList getItems() {
        return items;
    }

    public void load() {
        terrainBuffer.load();
        items.load();
    }

    public void updatePosition(Position pos) {
        if (pos.getLayer().equals(Layer.SURFACE)) {
            terrainBuffer.updatePosition(pos);
        } else {
            // TODO: Implement cave terrain
        }
    }

}
