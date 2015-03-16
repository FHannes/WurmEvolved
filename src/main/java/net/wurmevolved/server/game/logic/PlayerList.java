package net.wurmevolved.server.game.logic;

import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.PlayerDAO;
import net.wurmevolved.server.game.data.factory.PlayerFactory;
import net.wurmevolved.server.game.net.packets.AbstractPacket;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    public Collection<Player> all() {
        return players.values();
    }

    public Set<Player> getLocal(Position pos) {
        Set<Player> players = new HashSet<>();
        for (Player player : this.players.values()) {
            if (player.getPos().maxAxisDistance(pos) <= 50) {
                players.add(player);
            }
        }
        return players;
    }

    public void save(Player player) {
        dao.save(player);
    }

}
