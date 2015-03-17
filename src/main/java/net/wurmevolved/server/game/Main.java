package net.wurmevolved.server.game;

import net.wurmevolved.server.game.cli.CLIProcessor;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.net.RegisterServer;

public class Main {

    public static void main(String[] args) {
        String hostname = "localhost";
        if (args.length > 0) {
            hostname = args[0];
            if (!hostname.equals("localhost") && args.length == 4) {
                new RegisterServer(args[1], args[2], args[3]).register();
            }
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
