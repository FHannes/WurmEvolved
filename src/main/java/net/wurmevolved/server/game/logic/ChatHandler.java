package net.wurmevolved.server.game.logic;

import net.wurmevolved.common.constants.ChatColor;
import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.CommandCaller;
import net.wurmevolved.server.game.cli.CommandRegistry;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.net.packets.client.ClientMessagePacket;
import net.wurmevolved.server.game.net.packets.server.ServerMessagePacket;

import java.awt.*;

public class ChatHandler implements CommandCaller {

    private World world;
    private Player player;

    public ChatHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    private String parseMsg(String msg) {
        return String.format("<%s> %s", player.getUsername(), msg);
    }

    public void handle(ClientMessagePacket packet) {
        String msg = packet.getMessage();
        if (msg.equals("")) {
            return;
        }
        if (msg.startsWith("/")) {
            msg = msg.substring(1);
            AbstractCommand cmd = null;
            String args = "";
            if (!msg.equals("")) {
                String command = msg;
                int pos = msg.indexOf(' ');
                if (pos != -1) {
                    command = msg.substring(0, pos);
                    args = msg.substring(pos + 1);
                }
                command = command.toLowerCase();
                switch (command) {
                    case "server":
                        msg = parseMsg(args);
                        send(":Server", msg, ChatColor.WHITE);
                        sendAll(":Server", msg, ChatColor.WHITE);
                        return;
                }
                cmd = CommandRegistry.getInstance().get(command, player.getType());
            }
            if (cmd == null) {
                send("Unknown command.");
            } else {
                cmd.process(this, args);
            }
        } else {
            msg = parseMsg(msg);
            switch (packet.getChannel()) {
                case ":Local":
                    send(":Local", msg, ChatColor.WHITE);
                    sendLocal(":Local", msg, ChatColor.WHITE);
                    break;
                case ":Server":
                    send(":Server", msg, ChatColor.WHITE);
                    sendAll(":Server", msg, ChatColor.WHITE);
                    break;
            }
        }
    }

    public void send(String channel, String msg, Color color) {
        player.send(new ServerMessagePacket(channel, msg, color));
    }

    public void send(String msg) {
        send(":Event", msg, ChatColor.GREEN);
    }

    public void sendLocal(String channel, String msg, Color color) {
        ServerMessagePacket packet = new ServerMessagePacket(channel, msg, color);
        for (Player localPlayer : world.getPlayers().getLocal(player.getPos())) {
            if (localPlayer.equals(player)) {
                continue;
            }
            localPlayer.send(packet);
        }
    }

    public void sendLocal(String msg) {
        sendLocal(":Event", msg, ChatColor.GREEN);
    }

    public void sendAll(String channel, String msg, Color color) {
        ServerMessagePacket packet = new ServerMessagePacket(channel, msg, color);
        for (Player localPlayer : world.getPlayers().all()) {
            if (localPlayer.equals(player)) {
                continue;
            }
            localPlayer.send(packet);
        }
    }

    public void sendAll(String msg) {
        sendAll(":Event", msg, ChatColor.GREEN);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public PlayerType getType() {
        return player.getType();
    }

    @Override
    public void writeLine(String line) {
        send(line);
    }

}
