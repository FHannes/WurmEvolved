package com.wurmemu.server.game.data.factory

import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO

class PlayerFactory {

    private PlayerDAO dao

    PlayerFactory() {
        dao = DB.instance.getDAO("playerDAO")
    }

    Player makePlayer(String username) {
        def player = new Player(username: username)
        dao.save(player)
        player
    }

}
