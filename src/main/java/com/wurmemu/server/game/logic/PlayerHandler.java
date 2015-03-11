package com.wurmemu.server.game.logic;

import com.wurmemu.server.game.World;
import com.wurmemu.server.game.data.Player;
import com.wurmemu.server.game.data.Tile;
import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.PlayerDAO;
import com.wurmemu.server.game.data.factory.PlayerFactory;
import com.wurmemu.server.game.map.Chunk;
import com.wurmemu.server.game.map.TerrainBuffer;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.server.DistantTerrainPacket;
import com.wurmemu.server.game.net.packets.server.LoginResponsePacket;
import com.wurmemu.server.game.net.packets.server.TerrainPacket;
import io.netty.channel.Channel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerHandler {

    private static final PlayerFactory playerFactory = new PlayerFactory();

    private Channel channel;
    private Player player;
    private World world;
    private boolean developer;
    private boolean newPlayer;
    private Set<Chunk> chunks = new HashSet<>();
    private Point terrainPos;

    public PlayerHandler(Channel channel, String username, boolean developer) {
        PlayerDAO dao = (PlayerDAO) DB.getInstance().getDAO("playerDAO");
        player = dao.load(username);
        if (player == null) {
            player = playerFactory.makePlayer(username);
            newPlayer = true;
        } else {
            newPlayer = false;
        }
        this.channel = channel;
        this.developer = developer;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void send(AbstractPacket packet) {
        channel.writeAndFlush(packet);
    }

    public void login() {
        if (world != null) {
            if (newPlayer) {
                player.getPos().setX(512);
                player.getPos().setY(512);
                player.getPos().setLayer((byte) 0);
                world.updatePosition(player.getPos());
                save();
            }
            send(new LoginResponsePacket(true, "Welcome to WurmEvolved", player.getPos(), developer));
            updateTerrain();
            updateDistantTerrain();
        } else {
            // TODO: Prevent login, server-side error
        }
    }

    public void updateTerrain() {
        List<Chunk> chunks = world.getTerrainBuffer().getChunksFromCoords(getTileX(), getTileY(), (short) (192 / Chunk.CHUNK_SIZE));
        for (Chunk chunk : chunks) {
            if (!this.chunks.contains(chunk)) {
                sendChunk(chunk);
            }
        }
        this.chunks = new HashSet<>(chunks);
    }

    public void sendChunk(Chunk chunk) {
        send(new TerrainPacket(chunk.toArray()));
    }

    public void updateDistantTerrain() {
        if (terrainPos == null || terrainPos.distance(getTileX(), getTileY()) > 128) {
            terrainPos = new Point(getTileX(), getTileY());
            short x1 = (short) (Math.max(0, getTileX() - 1024) / 16);
            short y1 = (short) (Math.max(0, getTileY() - 1024) / 16);
            short map_size = Chunk.CHUNK_SIZE * TerrainBuffer.CHUNK_COUNT;
            short x2 = (short) (Math.min(map_size - 1, getTileX() + 1024) / 16);
            short y2 = (short) (Math.min(map_size - 1, getTileY() + 1024) / 16);
            Tile[][] tiles = new Tile[y2 - y1 + 1][x2 - x1 + 1];
            for (short x = x1; x <= x2; x++) {
                for (short y = y1; y <= y2; y++) {
                    tiles[y][x] = world.getTerrainBuffer().getTile((short) (x * 16), (short) (y * 16));
                }
            }
            send(new DistantTerrainPacket(tiles));
        }
    }

    public void save() {
        PlayerDAO dao = (PlayerDAO) DB.getInstance().getDAO("playerDAO");
        dao.save(player);
    }

    public void move(float x, float y, float z, byte layer) {
        player.getPos().setX(x);
        player.getPos().setZ(y);
        player.getPos().setX(z);
        player.getPos().setLayer(layer);
        if (!developer) {
            world.updatePosition(player.getPos());
        }
        updateTerrain();
        updateDistantTerrain();
    }

    public short getTileX() {
        return player.getPos().getTileX();
    }

    public short getTileY() {
        return player.getPos().getTileY();
    }

}
