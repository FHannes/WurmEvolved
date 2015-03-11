package com.wurmemu.server.game.logic;

import com.wurmemu.server.game.World;
import com.wurmemu.server.game.data.Player;
import com.wurmemu.server.game.data.Tile;
import com.wurmemu.server.game.map.Chunk;
import com.wurmemu.server.game.map.TerrainBuffer;
import com.wurmemu.server.game.net.packets.client.MovementPacket;
import com.wurmemu.server.game.net.packets.server.DistantTerrainPacket;
import com.wurmemu.server.game.net.packets.server.TerrainPacket;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovementHandler {

    private World world;
    private Player player;

    // Terrain data
    private Set<Chunk> chunks = new HashSet<>();
    private Point terrainPos;

    public MovementHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void updateTerrain() {
        List<Chunk> chunks = world.getTerrainBuffer().getChunksFromCoords(
                player.getPos().getTileX(), player.getPos().getTileY(), (short) (192 / Chunk.CHUNK_SIZE));
        for (Chunk chunk : chunks) {
            if (!this.chunks.contains(chunk)) {
                sendChunk(chunk);
            }
        }
        this.chunks = new HashSet<>(chunks);
    }

    public void sendChunk(Chunk chunk) {
        player.send(new TerrainPacket(chunk.toArray()));
    }

    public void updateDistantTerrain() {
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
                    tiles[y][x] = world.getTerrainBuffer().getTile((short) (x * 16), (short) (y * 16));
                }
            }
            player.send(new DistantTerrainPacket(tiles));
        }
    }

    public void handle(MovementPacket packet) {
        player.getPos().update(packet.getX() / 4, packet.getY() / 4);
        player.getPos().setZ(packet.getZ());
        player.getPos().setLayer(packet.getLayer());
        updateTerrain();
        updateDistantTerrain();
    }

}
