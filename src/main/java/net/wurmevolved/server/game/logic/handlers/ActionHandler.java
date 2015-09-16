package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.common.constants.ActionType;
import net.wurmevolved.common.constants.EntityType;
import net.wurmevolved.common.constants.ItemType;
import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.logic.actions.AbstractAction;
import net.wurmevolved.server.game.logic.actions.ActionQueue;
import net.wurmevolved.server.game.logic.actions.ActionRegistry;
import net.wurmevolved.server.game.logic.list.GlobalEntityList;
import net.wurmevolved.server.game.menu.ActionItem;
import net.wurmevolved.server.game.menu.Menu;
import net.wurmevolved.server.game.menu.MenuFactory;
import net.wurmevolved.server.game.menu.MenuItem;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.client.RequestActionPacket;
import net.wurmevolved.server.game.net.packets.client.SendActionPacket;
import net.wurmevolved.server.game.net.packets.server.SendActionListPacket;

import javax.persistence.EntityListeners;
import java.util.ArrayList;
import java.util.List;

public class ActionHandler extends LogicHandler {

    private World world;
    private Player player;

    private MenuFactory menuFactory;

    public ActionHandler(World world, Player player) {
        this.world = world;
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
            RequestActionPacket packetRA = (RequestActionPacket) packet;
            if (EntityType.isIDOfType(packetRA.getTargetID(), EntityType.ITEM) &&
                    GlobalEntityList.items.get(packetRA.getTargetID()).getPos().getLayer().equals(Layer.NONE)) {
                items.add(new ActionItem(ActionType.DROP));
            }
            menu.buildMenu(items);
            player.send(new SendActionListPacket(((RequestActionPacket) packet).getRequestID(), items, menu.getWiki()));
        } else if (packet instanceof SendActionPacket) {
            ActionQueue.getInstance().queue(
                    ActionRegistry.getInstance().makeAction(world, player, (SendActionPacket) packet)
            );
        }
    }

    @Override
    public void leave() {

    }

}
