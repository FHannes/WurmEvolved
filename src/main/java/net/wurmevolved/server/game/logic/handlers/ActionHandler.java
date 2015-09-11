package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.menu.Menu;
import net.wurmevolved.server.game.menu.MenuFactory;
import net.wurmevolved.server.game.menu.MenuItem;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.client.RequestActionPacket;
import net.wurmevolved.server.game.net.packets.client.SendActionPacket;
import net.wurmevolved.server.game.net.packets.server.SendActionListPacket;

import java.util.ArrayList;
import java.util.List;

public class ActionHandler extends LogicHandler {

    private Player player;

    private MenuFactory menuFactory;

    public ActionHandler(Player player) {
        this.player = player;
        this.menuFactory = new MenuFactory();
    }

    @Override
    public void join() {

    }

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof RequestActionPacket) {
            Menu menu = menuFactory.makeMenu("");
            List<MenuItem> items = new ArrayList<>();
            menu.buildMenu(items);
            player.send(new SendActionListPacket(((RequestActionPacket) packet).getRequestID(), items, menu.getWiki()));
        }
    }

    @Override
    public void leave() {

    }

}
