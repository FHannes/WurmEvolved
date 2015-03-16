package net.wurmevolved.server.game;

import net.wurmevolved.server.game.data.db.DB;

public class Main {

    public static void main(String[] args) {
        new Server("", 48001).start();

        DB.getInstance().close();
    }

}
