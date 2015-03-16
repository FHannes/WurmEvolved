package net.wurmevolved.server.game;

import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.logic.PlayerList;
import net.wurmevolved.server.game.map.TerrainBuffer;

public class World {

    private PlayerList players;
    private TerrainBuffer terrainBuffer;

    public World() {
        players = new PlayerList();
        terrainBuffer = new TerrainBuffer();
    }

    public PlayerList getPlayers() {
        return players;
    }

    public TerrainBuffer getTerrainBuffer() {
        return terrainBuffer;
    }

    public void load() {
        terrainBuffer.load();
    }

    public void updatePosition(Position pos) {
        if (pos.getLayer() == 0) {
            terrainBuffer.updatePosition(pos);
        } else {
            // TODO: Implement cave terrain
        }
    }

}
