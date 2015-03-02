package com.wurmemu.server.game.logic

import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO
import com.wurmemu.server.game.data.factory.PlayerFactory
import com.wurmemu.server.game.net.Packet
import io.netty.channel.Channel

class PlayerHandler {

    static PlayerFactory playerFactory = new PlayerFactory()

    Channel channel
    Player player
    boolean developer

    PlayerHandler(Channel channel, String username, boolean developer) {
        PlayerDAO dao = DB.instance.getDAO("playerDAO")
        player = dao.load(username)
        if (player == null) {
            player = playerFactory.makePlayer(username)
        }
        this.channel = channel
        this.developer = developer
    }

    void send(Packet packet) {
        channel.writeAndFlush(packet)
    }

}
