package com.wurmemu.server.game;

import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.logic.PlayerHandler;
import com.wurmemu.server.game.map.TerrainBuffer;
import com.wurmemu.server.game.net.packets.AbstractPacket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private Set<PlayerHandler> players;
    private TerrainBuffer terrainBuffer;

    public World() {
        players = Collections.newSetFromMap(new ConcurrentHashMap<>());
        terrainBuffer = new TerrainBuffer();
    }

    public TerrainBuffer getTerrainBuffer() {
        return terrainBuffer;
    }

    public Set<PlayerHandler> getPlayers() {
        return new HashSet<>(players);
    }

    public void addPlayer(PlayerHandler player) {
        players.add(player);
        player.setWorld(this);
    }

    public void removePlayer(PlayerHandler player) {
        player.setWorld(null);
        players.remove(player);
    }

    public void broadcast(AbstractPacket packet) {
        for (PlayerHandler player : players) {
            player.send(packet);
        }
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

    public List<PlayerHandler> getPlayersInLocal(Position pos) {
        List<PlayerHandler> players = new ArrayList<>();
        for (PlayerHandler player : players) {
            if (player.getPlayer().getPos().maxAxisDistance(pos) <= 50) {
                players.add(player);
            }
        }
        return players;
    }

}
