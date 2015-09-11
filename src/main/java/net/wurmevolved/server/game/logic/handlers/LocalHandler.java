package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.common.constants.CreatureType;
import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.*;
import net.wurmevolved.server.game.logic.GameEntity;
import net.wurmevolved.server.game.logic.observers.MovementObserver;
import net.wurmevolved.server.game.logic.observers.impl.LocalObjectObserver;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.server.*;
import net.wurmevolved.server.game.util.PlayerHelper;

import java.util.HashSet;
import java.util.Set;

public class LocalHandler extends LogicHandler implements MovementObserver {

    private World world;
    private Player player;

    public LocalHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void updateItems(Position pos, int diffX, int diffY) {
        int prevX = pos.getTileX() - diffX, prevY = pos.getTileY() - diffY;

        if (diffX != 0) {
            diffX = prevX + Layer.SURFACE.getLocal() * diffX;
            for (Tile tile : world.getLocal(player.getPos())) {
                if (tile.getPos().getX() == diffX) {
                    for (AbstractItem localItem : tile.getItems()) {
                        player.addLocal(localItem);
                        player.send(new AddObjectPacket(localItem.getId(), localItem.getModel(), localItem.getPos(),
                                localItem.getItemName(), localItem.getMaterial()));
                    }
                }
            }
        } else {
            diffY = prevY + Layer.SURFACE.getLocal() * diffY;
            for (Tile tile : world.getLocal(player.getPos())) {
                if (tile.getPos().getY() == diffY) {
                    for (AbstractItem localItem : tile.getItems()) {
                        player.addLocal(localItem);
                        player.send(new AddObjectPacket(localItem.getId(), localItem.getModel(), localItem.getPos(),
                                localItem.getItemName(), localItem.getMaterial()));
                    }
                }
            }
        }
    }

    /**
     * Updates all other players if this player has moved in or out of their local. This will also send the movements of
     * the player to all other players in local.
     */
    public void updatePlayerLocals() {
        Set<Player> localPlayers;

        // Add or remove players from local
        localPlayers = world.getPlayers().getLocal(player.getPos());
        AddCreaturePacket packetAdd = new AddCreaturePacket(
                player.getId(), player.getModel(), player.getPos(), player.getFullName(), CreatureType.HUMAN,
                player.getKingdom(), player.getFaceStyle());
        RemoveCreaturePacket packetRemove = new RemoveCreaturePacket(player.getId());
        AddUserPacket packetAddUser = new AddUserPacket(":Local", player.getUsername(), player.getId());
        RemoveUserPacket packetRemoveUser = new RemoveUserPacket(":Local", player.getUsername());
        for (Player worldPlayer : world.getPlayers().all()) {
            if (worldPlayer.equals(player)) {
                continue;
            }
            if (localPlayers.contains(worldPlayer)) {
                boolean hasPlayer = worldPlayer.hasLocal(player);
                if (!hasPlayer) {
                    worldPlayer.addLocal(player);
                    worldPlayer.send(packetAdd);
                    worldPlayer.send(packetAddUser);
                    player.send(new AddCreaturePacket(
                            worldPlayer.getId(), worldPlayer.getModel(), worldPlayer.getPos(),
                            worldPlayer.getUsername(), CreatureType.HUMAN, worldPlayer.getKingdom(),
                            worldPlayer.getFaceStyle()));
                    player.send(new AddUserPacket(":Local", worldPlayer.getUsername(), worldPlayer.getId()));
                }
            } else {
                boolean hasPlayer = worldPlayer.hasLocal(player);
                if (hasPlayer) {
                    worldPlayer.removeLocal(player);
                    worldPlayer.send(packetRemoveUser);
                    worldPlayer.send(packetRemove);
                    player.send(new RemoveCreaturePacket(worldPlayer.getId()));
                    player.send(new RemoveUserPacket(":Local", worldPlayer.getUsername()));
                }
            }
        }
    }

    @Override
    public void onPlayerMovedTile(Position pos, int xOffset, int yOffset) {
        updatePlayerLocals();
        updateItems(pos, xOffset, yOffset);
    }

    @Override
    public void join() {
        // Init local players
        for (Player localPlayer : world.getPlayers().getLocal(player.getPos())) {
            if (!player.equals(localPlayer)) {
                player.addLocal(localPlayer);
                player.send(new AddCreaturePacket(
                        localPlayer.getId(), localPlayer.getModel(), localPlayer.getPos(), localPlayer.getFullName(),
                        CreatureType.HUMAN, localPlayer.getKingdom(), localPlayer.getFaceStyle()));
            }
            player.send(new AddUserPacket(":Local", localPlayer.getUsername(), localPlayer.getId()));
        }

        // Init local items
        for (Tile tile : world.getLocal(player.getPos())) {
            for (AbstractItem localItem : tile.getItems()) {
                player.addLocal(localItem);
                player.send(new AddObjectPacket(localItem.getId(), localItem.getModel(), localItem.getPos(),
                        localItem.getItemName(), localItem.getMaterial()));
                localItem.getObservers().add(new LocalObjectObserver(player));
            }
        }

        // Init locals of other players
        updatePlayerLocals();
    }

    @Override
    public void handle(AbstractPacket packet) {

    }

    @Override
    public void leave() {

    }

}
