package com.wurmemu.server.game.logic

import com.wurmemu.server.game.World
import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO
import com.wurmemu.server.game.data.factory.PlayerFactory
import com.wurmemu.server.game.map.Chunk
import com.wurmemu.server.game.net.Packet
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
            }
            send(new LoginResponsePacket(
                    allowLogin: true, reason: "Welcome to WurmEvolved",
                    layer: 0,
                    x: player.x * 4, y: player.y * 4, z: player.z,
                    developer: developer))
            updateChunks()
        } else {
            // TODO: Prevent login, server-side error
        }
    }

    void updateChunks() {
        def chunks = world.terrainBuffer.getChunksFromCoords(
                (int) Math.floor(player.x), (int) Math.floor(player.y), 8)
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

}
