package com.wurmemu.server.game.logic

import com.wurmemu.server.game.World
import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO
import com.wurmemu.server.game.data.factory.PlayerFactory
import com.wurmemu.server.game.map.Chunk
import com.wurmemu.server.game.map.TerrainBuffer
import com.wurmemu.server.game.net.Packet
import com.wurmemu.server.game.net.packets.server.DistantTerrainPacket
import com.wurmemu.server.game.net.packets.server.LoginResponsePacket
import com.wurmemu.server.game.net.packets.server.TerrainPacket
import io.netty.channel.Channel

class PlayerHandler {

    static PlayerFactory playerFactory = new PlayerFactory()

    Channel channel
    Player player
    World world
    boolean developer
    boolean newPlayer
    Set<Chunk> chunks = new HashSet<>()

    PlayerHandler(Channel channel, String username, boolean developer) {
        PlayerDAO dao = DB.instance.getDAO("playerDAO")
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

    void send(Packet packet) {
        channel.writeAndFlush(packet)
    }

    void login() {
        if (world != null) {
            if (newPlayer) {
                player.x = 512
                player.y = 512
                player.z = world.terrainBuffer.getTile(512, 512).height / 10
                player.layer = 0
                save()
            }
            send(new LoginResponsePacket(
                    allowLogin: true, reason: "Welcome to WurmEvolved",
                    layer: player.layer,
                    x: player.x * 4, y: player.y * 4, z: player.z,
                    developer: developer))
            updateTerrain()
            updateDistantTerrain()
        } else {
            // TODO: Prevent login, server-side error
        }
    }

    void updateTerrain() {
        def chunks = world.terrainBuffer.getChunksFromCoords(getTileX(), getTileY(), (int) 256 / Chunk.CHUNK_SIZE)
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

    void save() {
        PlayerDAO dao = DB.instance.getDAO("playerDAO")
        dao.save(player)
    }

    void move(float x, float y, float z, byte layer) {
        player.x = x
        player.y = y
        player.z = z
        player.layer = layer
        updateTerrain()
        updateDistantTerrain()
    }

    short getTileX() {
        (short) Math.floor(player.x)
    }

    short getTileY() {
        (short) Math.floor(player.y)
    }

}
