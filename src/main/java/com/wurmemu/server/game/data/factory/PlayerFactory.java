package com.wurmemu.server.game.data.factory;

import com.wurmemu.common.constants.EntityType;
import com.wurmemu.server.game.data.Player;
import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.PlayerDAO;

public class PlayerFactory {

    private PlayerDAO dao;
    private IDFactory idFactory;

    public PlayerFactory() {
        dao = (PlayerDAO) DB.getInstance().getDAO("playerDAO");
        idFactory = new IDFactory("player", EntityType.PLAYER);
    }

    public Player makePlayer(String username) {
        Player player = new Player();
        player.setId(idFactory.makeID());
        player.setUsername(username);
        dao.save(player);
        return player;
    }

}
