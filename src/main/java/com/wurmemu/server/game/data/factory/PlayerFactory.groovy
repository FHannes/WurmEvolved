package com.wurmemu.server.game.data.factory

import com.wurmemu.common.constants.EntityType
import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO

class PlayerFactory {

    private PlayerDAO dao
    private IDFactory idFactory

    PlayerFactory() {
        dao = DB.instance.getDAO("playerDAO")
        idFactory = new IDFactory("player", EntityType.PLAYER)
    }

    Player makePlayer(String username) {
        def player = new Player(id: idFactory.makeID(), username: username)
        dao.save(player)
        player
    }

}
