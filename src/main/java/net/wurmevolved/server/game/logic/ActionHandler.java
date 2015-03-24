package net.wurmevolved.server.game.logic;

import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.menu.Menu;
import net.wurmevolved.server.game.menu.MenuFactory;
import net.wurmevolved.server.game.menu.MenuItem;
import net.wurmevolved.server.game.net.packets.client.RequestActionPacket;
import net.wurmevolved.server.game.net.packets.client.SendActionPacket;
import net.wurmevolved.server.game.net.packets.server.SendActionListPacket;

import java.util.ArrayList;
import java.util.List;

public class ActionHandler {

    private World world;
    private Player player;

    private MenuFactory menuFactory;

    public ActionHandler(World world, Player player) {
        this.world = world;
        this.player = player;
        this.menuFactory = new MenuFactory();
    }

    public void handle(RequestActionPacket packet) {
        Menu menu = menuFactory.makeMenu("");
        List<MenuItem> items = new ArrayList<>();
        menu.buildMenu(items);
        player.send(new SendActionListPacket(packet.getRequestID(), items, menu.getWiki()));
    }

    public void handle(SendActionPacket packet) {

    }

}
