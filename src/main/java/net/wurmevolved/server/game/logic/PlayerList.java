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
    private Map<String, Long> nameMap = new ConcurrentHashMap<>();
    private PlayerDAO dao = (PlayerDAO) DB.getInstance().getDAO("playerDAO");
    private PlayerFactory playerFactory = new PlayerFactory();

    public Player load(String username) {
        Player player = dao.load(username);
        if (player == null) {
            player = playerFactory.makePlayer(username);
            dao.save(player);
        }
        players.put(player.getId(), player);
        nameMap.put(player.getUsername(), player.getId());
        return player;
    }

    public void remove(Player player) {
        remove(player.getId());
    }

    public void remove(long id) {
        Player player = players.get(id);
        if (player != null) {
            players.remove(id);
            nameMap.remove(player.getUsername());
        }
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

    public Player get(long id) {
        return players.get(id);
    }

    public Player get(String username) {
        if (nameMap.containsKey(username)) {
            return get(nameMap.get(username));
        }
        return null;
    }

}
