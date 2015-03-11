package com.wurmemu.server.game.logic;

import com.wurmemu.server.game.data.Player;
import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.PlayerDAO;
import com.wurmemu.server.game.data.factory.PlayerFactory;
import com.wurmemu.server.game.net.packets.AbstractPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerList {

    private Map<Long, Player> players = new ConcurrentHashMap<>();
    private PlayerDAO dao = (PlayerDAO) DB.getInstance().getDAO("playerDAO");
    private PlayerFactory playerFactory = new PlayerFactory();

    public Player load(String username) {
        Player player = dao.load(username);
        if (player == null) {
            player = playerFactory.makePlayer(username);
            dao.save(player);
        }
        players.put(player.getId(), player);
        return player;
    }

    public void remove(Player player) {
        remove(player.getId());
    }

    public void remove(long id) {
        players.remove(id);
    }

    public void broadcast(AbstractPacket packet) {
        for (Player player : players.values()) {
            player.send(packet);
        }
    }

    public List<Player> getLocal(Position pos) {
        List<Player> players = new ArrayList<>();
        for (Player player : players) {
            if (player.getPos().maxAxisDistance(pos) <= 50) {
                players.add(player);
            }
        }
        return players;
    }

}
