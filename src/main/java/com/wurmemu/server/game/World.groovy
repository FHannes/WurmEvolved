package com.wurmemu.server.game

import com.wurmemu.server.game.logic.PlayerHandler
import com.wurmemu.server.game.map.TerrainBuffer
import com.wurmemu.server.game.net.packets.AbstractPacket

import java.util.concurrent.ConcurrentHashMap

class World {

    private Set<PlayerHandler> players
    TerrainBuffer terrainBuffer

    World() {
        players = Collections.newSetFromMap(new ConcurrentHashMap<PlayerHandler, Boolean>())
        terrainBuffer = new TerrainBuffer()
    }

    Set<PlayerHandler> getPlayers() {
        return new HashSet<PlayerHandler>(players)
    }

    void addPlayer(PlayerHandler player) {
        players.add(player)
        player.world = this
    }

    void removePlayer(PlayerHandler player) {
        player.world = null
        players.remove(player)
    }

    void broadcast(AbstractPacket packet) {
        players.each { player -> player.send(packet) }
    }

    void load() {
        terrainBuffer.load()
    }

}
