package com.wurmemu.server.game

import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.PlayerDAO;

class Main {

    static void main(String[] args) {
        PlayerDAO playerDAO = DB.instance.getDAO("playerDAO")

        new Server(hostname: "localhost", port: 48001).start()

        DB.instance.close()
    }

}
