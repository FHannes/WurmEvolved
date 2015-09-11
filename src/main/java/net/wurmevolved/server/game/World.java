package net.wurmevolved.server.game;

import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.logic.PlayerList;
import net.wurmevolved.server.game.logic.handlers.ObjectHandler;
import net.wurmevolved.server.game.map.Chunk;
import net.wurmevolved.server.game.map.TerrainBuffer;

import java.util.HashSet;
import java.util.Set;

public class World {

    private PlayerList players;
    private TerrainBuffer terrainBuffer;
    private ObjectHandler objectHandler;

    public World() {
        players = new PlayerList();
        terrainBuffer = new TerrainBuffer();
        objectHandler = new ObjectHandler(this);
    }

    public PlayerList getPlayers() {
        return players;
    }

    public TerrainBuffer getTerrainBuffer() {
        return terrainBuffer;
    }

    public ObjectHandler getObjectHandler() {
        return objectHandler;
    }

    public void load() {
        terrainBuffer.load();
    }

    public void updatePosition(Position pos) {
        if (pos.getLayer().equals(Layer.SURFACE)) {
            terrainBuffer.updatePosition(pos);
        } else {
            // TODO: Implement cave terrain
        }
    }

    public Set<Tile> getLocal(Position pos) { // TODO: Seems to be broken...
        Set<Tile> tiles = new HashSet<>();

        short local = pos.getLayer().getLocal();
        short cx = pos.getTileX(), cy = pos.getTileY();
        short sx = (short) (cx - local), sy = (short) (cy - local);
        short ex = (short) (cx + local), ey = (short) (cy + local);

        short scx = Chunk.mapToChunk(sx), scy = Chunk.mapToChunk(sy);
        short ecx = Chunk.mapToChunk(ex), ecy = Chunk.mapToChunk(ey);

        for (short ly = scy; ly < ecy; ly++) {
            for (short lx = scx; lx < ecx; lx++) {
                Chunk chunk = terrainBuffer.getChunk(lx, ly);
                for (short ry = 0; ry < Chunk.CHUNK_SIZE; ry++) {
                    for (short rx = 0; rx < Chunk.CHUNK_SIZE; rx++) {
                        tiles.add(chunk.getTile(rx, ry));
                    }
                }
            }
        }

        return tiles;
    }

}
