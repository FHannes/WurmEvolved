package net.wurmevolved.server.game.cli;

import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.Server;
import net.wurmevolved.server.game.cli.commands.QuitCommand;

import java.io.*;

public class CLIProcessor {

    private Server server;

    private BufferedReader reader;
    private BufferedWriter writer;

    public CLIProcessor(Server server, InputStream is, OutputStream os) {
        this.server = server;
        reader = new BufferedReader(new InputStreamReader(is));
        writer = new BufferedWriter(new OutputStreamWriter(os));
    }

    public CLIProcessor(Server server) {
        this(server, System.in, System.out);
    }

    public Server getServer() {
        return server;
    }

    public void run() {
        try {
            writer.write("Command-line processor started");
            writer.newLine();
            writer.write("> ");
            writer.flush();
            while (true) {
                String command = reader.readLine();
                int pos = command.indexOf(' ');
                String args = "";
                if (pos != -1) {
                    args = command.substring(pos + 1);
                    command = command.substring(0, pos);
                }
                AbstractCommand cmdHandler = CommandRegistry.getInstance().get(command, PlayerType.DEV);
                if (cmdHandler != null) {
                    if (cmdHandler instanceof QuitCommand) {
                        break;
                    }
                    cmdHandler.process(this, args);
                } else {
                    writeLine("Unknown command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String line, boolean flush) {
        try {
            writer.write(line);
            writer.newLine();
            if (flush) {
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String line) {
        writeLine(line, true);
    }

}
