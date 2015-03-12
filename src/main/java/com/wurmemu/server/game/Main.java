package com.wurmemu.server.game;

import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.PlayerDAO;

public class Main {

    public static void main(String[] args) {
        new Server("localhost", 48001).start();

        DB.getInstance().close();
    }

}
