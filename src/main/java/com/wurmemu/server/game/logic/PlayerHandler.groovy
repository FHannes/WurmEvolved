package com.wurmemu.server.game.logic

import com.wurmemu.server.game.World
import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO
import com.wurmemu.server.game.data.factory.PlayerFactory
import com.wurmemu.server.game.map.Chunk
import com.wurmemu.server.game.map.TerrainBuffer
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.server.DistantTerrainPacket
import com.wurmemu.server.game.net.packets.server.LoginResponsePacket
import com.wurmemu.server.game.net.packets.server.TerrainPacket
import io.netty.channel.Channel

import java.awt.*

class PlayerHandler {

    static PlayerFactory playerFactory = new PlayerFactory()

    Channel channel
    Player player
    World world
    boolean developer
    boolean newPlayer
    Set<Chunk> chunks = new HashSet<>()
    Point terrainPos

    PlayerHandler(Channel channel, String username, boolean developer) {
        PlayerDAO dao = (PlayerDAO) DB.instance.getDAO("playerDAO")
        player = dao.load(username)
        if (player == null) {
            player = playerFactory.makePlayer(username)
            newPlayer = true
        } else {
            newPlayer = false
        }
        this.channel = channel
        this.developer = developer
    }

    void send(AbstractPacket packet) {
        channel.writeAndFlush(packet)
    }

    void login() {
        if (world != null) {
            if (newPlayer) {
                player.pos.x = 512
                player.pos.y = 512
                player.pos.layer = 0
                world.updatePosition(player.pos)
                save()
            }
            send(new LoginResponsePacket(
                    allowLogin: true, reason: "Welcome to WurmEvolved",
                    pos: player.pos, developer: developer))
            updateTerrain()
            updateDistantTerrain()
        } else {
            // TODO: Prevent login, server-side error
        }
    }

    void updateTerrain() {
        def chunks = world.terrainBuffer.getChunksFromCoords(getTileX(), getTileY(), (int) 192 / Chunk.CHUNK_SIZE)
        chunks.each { chunk ->
            if (!this.chunks.contains(chunk)) {
                sendChunk(chunk)
            }
        }
        this.chunks = new HashSet<>(chunks)
    }

    void sendChunk(Chunk chunk) {
        send(new TerrainPacket(tiles: chunk.toArray()))
    }

    void updateDistantTerrain() {
        if (terrainPos == null || terrainPos.distance(getTileX(), getTileY()) > 128) {
            terrainPos = new Point(getTileX(), getTileY())
            def x1 = (short) Math.max(0, getTileX() - 1024) / 16
            def y1 = (short) Math.max(0, getTileY() - 1024) / 16
            def map_size = Chunk.CHUNK_SIZE * TerrainBuffer.CHUNK_COUNT
            def x2 = (short) Math.min(map_size - 1, getTileX() + 1024) / 16
            def y2 = (short) Math.min(map_size - 1, getTileY() + 1024) / 16
            def tiles = new Tile[y2 - y1 + 1][x2 - x1 + 1]
            (x1..x2).each { x ->
                (y1..y2).each { y ->
                    tiles[y][x] = world.terrainBuffer.getTile(x * 16, y * 16)
                }
            }
            send(new DistantTerrainPacket(tiles: tiles))
        }
    }

    void save() {
        PlayerDAO dao = (PlayerDAO) DB.instance.getDAO("playerDAO")
        dao.save(player)
    }

    void move(float x, float y, float z, byte layer) {
        player.pos.x = x
        player.pos.y = y
        player.pos.z = z
        player.pos.layer = layer
        if (!developer) {
            world.updatePosition(player.pos)
        }
        updateTerrain()
        updateDistantTerrain()
    }

    short getTileX() {
        player.pos.tileX
    }

    short getTileY() {
        player.pos.tileY
    }

}
