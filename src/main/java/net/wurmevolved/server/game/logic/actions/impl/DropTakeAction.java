package net.wurmevolved.server.game.logic.actions.impl;

import net.wurmevolved.common.constants.ActionType;
import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.logic.actions.AbstractAction;
import net.wurmevolved.server.game.logic.actions.Action;
import net.wurmevolved.server.game.logic.list.GlobalEntityList;
import net.wurmevolved.server.game.net.packets.client.SendActionPacket;
import net.wurmevolved.server.game.net.packets.server.AddObjectPacket;
import net.wurmevolved.server.game.net.packets.server.RemoveItemPacket;

@Action(ActionType.DROP)
public class DropTakeAction extends AbstractAction {

    private Player player;
    private SendActionPacket packet;

    public DropTakeAction(World world, Player player, SendActionPacket packet) {
        super(world, 0);
        this.player = player;
        this.packet = packet;
    }

    @Override
    protected void execute() {
        for (long targetID : packet.getTargetIDs()) {
            AbstractItem target = GlobalEntityList.items.get(targetID);
            if (target != null) {
                if (target.getPos().getLayer().equals(Layer.NONE)) {
                    if (target.hasParent(player.getInventory())) {
                        player.send(new RemoveItemPacket(-1L, target.getId()));
                        target.getParent().removeItem(target);
                        target.setPos(player.getPos());
                        getWorld().getTerrainBuffer().getTile(player.getPos()).addItem(target);
                        AddObjectPacket packetAddObject = new AddObjectPacket(target.getId(), target.getModel(),
                                target.getPos(), target.getItemName(), target.getMaterial());
                        getWorld().getPlayers().getContainedLocal(target.getPos()).forEach(p -> {
                            p.addLocal(target);
                            p.send(packetAddObject);
                        });
                    } else {
                        // TODO: Dropping item from foreign container?
                    }
                } else {
                    // TODO: Take item from ground?
                }
            }
        }
    }

}
