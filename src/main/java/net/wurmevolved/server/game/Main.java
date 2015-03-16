package net.wurmevolved.server.game;

import net.wurmevolved.server.game.cli.CLIProcessor;
import net.wurmevolved.server.game.data.db.DB;

public class Main {

    public static void main(String[] args) {
        String hostname = "localhost";
        if (args.length == 1) {
            hostname = args[0];
        }
        Server server = new Server(hostname, 48001);
        try {
            server.start();
            new CLIProcessor(server).run();
        } finally {
            server.stop();
            DB.getInstance().close();
        }
    }

}
