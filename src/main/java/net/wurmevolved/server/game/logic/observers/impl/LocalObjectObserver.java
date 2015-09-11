package net.wurmevolved.server.game.logic.observers.impl;

import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.logic.observers.ItemObserver;
import net.wurmevolved.server.game.net.packets.server.AddObjectPacket;

public class LocalObjectObserver implements ItemObserver {

    private Player player;

    public LocalObjectObserver(Player player) {
        this.player = player;
    }

    @Override
    public void onAddedToWorld(AbstractItem item, Position pos) {
        if (player.getPos().isLocal(pos)) {
            player.addLocal(item);
            player.send(new AddObjectPacket(item.getId(), item.getModel(), item.getPos(), item.getItemName(),
                    item.getMaterial()));
        }
    }

    @Override
    public void onRemovedFromWorld(AbstractItem item) {
        player.removeLocal(item);
        item.getObservers().remove(this);
    }

    @Override
    public void onChangedWorldPosition(AbstractItem item, Position posOld, Position posNew) {
        if (player.getPos().isLocal(posNew)) {
            // TODO: Move object
        } else {
            onRemovedFromWorld(item);
        }
    }

}