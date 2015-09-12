package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.logic.observers.MovementObserver;
import net.wurmevolved.server.game.map.Chunk;
import net.wurmevolved.server.game.map.TerrainBuffer;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.server.DistantTerrainPacket;
import net.wurmevolved.server.game.net.packets.server.TerrainPacket;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TerrainHandler extends LogicHandler implements MovementObserver {

    private World world;
    private Player player;

    private Set<Chunk> chunks = new HashSet<>();
    private Point terrainPos;

    public TerrainHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    private void updateTerrain() {
        java.util.List<Chunk> chunks = world.getTerrainBuffer().getChunksFromCoords(
                player.getPos().getTileX(), player.getPos().getTileY(), (short) (192 / Chunk.CHUNK_SIZE));
        chunks.forEach(chunk -> {
            if (!this.chunks.contains(chunk)) {
                sendChunk(chunk);
            }
        });
        this.chunks = new HashSet<>(chunks);
    }

    private void sendChunk(Chunk chunk) {
        player.send(new TerrainPacket(chunk.toArray()));
    }

    private void updateDistantTerrain() {
        if (terrainPos == null || terrainPos.distance(player.getPos().getTileX(), player.getPos().getTileY()) > 128) {
            terrainPos = new Point(player.getPos().getTileX(), player.getPos().getTileY());
            short x1 = (short) (Math.max(0, player.getPos().getTileX() - 1024) / 16);
            short y1 = (short) (Math.max(0, player.getPos().getTileY() - 1024) / 16);
            short map_size = Chunk.CHUNK_SIZE * TerrainBuffer.CHUNK_COUNT;
            short x2 = (short) (Math.min(map_size - 1, player.getPos().getTileX() + 1024) / 16);
            short y2 = (short) (Math.min(map_size - 1, player.getPos().getTileY() + 1024) / 16);
            Tile[][] tiles = new Tile[y2 - y1 + 1][x2 - x1 + 1];
            for (short x = x1; x <= x2; x++) {
                for (short y = y1; y <= y2; y++) {
                    tiles[y - y1][x - x1] = world.getTerrainBuffer().getTile((short) (x * 16), (short) (y * 16));
                }
            }
            player.send(new DistantTerrainPacket(tiles));
        }
    }

    private void update() {
        updateTerrain();
        updateDistantTerrain();
    }

    @Override
    public void onPlayerMovedTile() {
        update();
    }

    @Override
    public void join() {
        update();
    }

    @Override
    public void handle(AbstractPacket packet) {

    }

    @Override
    public void leave() {

    }

}
