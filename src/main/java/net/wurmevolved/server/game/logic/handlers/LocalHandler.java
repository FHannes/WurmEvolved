package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.common.constants.CreatureType;
import net.wurmevolved.common.constants.Layer;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.logic.GameEntity;
import net.wurmevolved.server.game.logic.observers.MovementObserver;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.server.*;
import net.wurmevolved.server.game.util.PlayerHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalHandler extends LogicHandler implements MovementObserver {

    private World world;
    private Player player;

    private Set<Tile> localTiles = new HashSet<>();

    public LocalHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void updateObjects() {
        world.getLocal(player.getPos()).forEach(t -> {
            if (!localTiles.contains(t)) {
                localTiles.add(t);

                // Ground items
                for (AbstractItem localItem : t.getItems()) {
                    player.addLocal(localItem);
                    player.send(new AddObjectPacket(localItem.getId(), localItem.getModel(), localItem.getPos(),
                            localItem.getItemName(), localItem.getMaterial()));
                    //localItem.getObservers().add(new LocalObjectObserver(player)); // TODO: Replace with something clean
                }
            }
        });
    }

    public void updatePlayers() {
        AddCreaturePacket packetAdd = new AddCreaturePacket(
                player.getId(), player.getModel(), player.getPos(), player.getFullName(), CreatureType.HUMAN,
                player.getKingdom(), player.getFaceStyle());
        AddUserPacket packetAddUser = new AddUserPacket(":Local", player.getUsername(), player.getId());
        world.getPlayers().getLocal(player.getPos()).forEach(worldPlayer -> {
            if (worldPlayer.equals(player)) {
                return;
            }
            if (!worldPlayer.hasLocal(player)) {
                // Add player to other player's local
                worldPlayer.addLocal(player);
                worldPlayer.send(packetAdd);
                worldPlayer.send(packetAddUser);
            }
            if (!player.hasLocal(worldPlayer)) {
                // Add other player to player's local
                player.addLocal(worldPlayer);
                player.send(new AddCreaturePacket(
                        worldPlayer.getId(), worldPlayer.getModel(), worldPlayer.getPos(),
                        worldPlayer.getUsername(), CreatureType.HUMAN, worldPlayer.getKingdom(),
                        worldPlayer.getFaceStyle()));
                player.send(new AddUserPacket(":Local", worldPlayer.getUsername(), worldPlayer.getId()));
            }
        });
    }

    public void pruneLocals() {
        List<GameEntity> removeLocals = new ArrayList<>();

        // Remove tiles from local tile cache
        List<Tile> removeTiles = new ArrayList<>();
        localTiles.forEach(t -> {
            if (!player.getPos().isLocal(t.getPos(), Layer.SURFACE)) {
                removeTiles.add(t);
            }
        });
        removeTiles.forEach(localTiles::remove);

        // Remove players no longer in local
        RemoveCreaturePacket packetRemove = new RemoveCreaturePacket(player.getId());
        RemoveUserPacket packetRemoveUser = new RemoveUserPacket(":Local", player.getUsername());
        player.getLocal(Player.class).forEach(p -> {
            if (!player.getPos().isLocal(p.getPos())) {
                p.removeLocal(player);
                p.send(packetRemoveUser);
                p.send(packetRemove);
                player.send(new RemoveCreaturePacket(p.getId()));
                player.send(new RemoveUserPacket(":Local", p.getUsername()));
                removeLocals.add(p);
            }
        });

        // Remove items no longer in local
        PlayerHelper.getLocalItems(player).forEach(i -> {
            if (!player.getPos().isLocal(i.getPos())) {
                player.send(new RemoveObjectPacket(i.getId()));
                removeLocals.add(i);
            }
        });

        removeLocals.forEach(player::removeLocal);
    }

    private void updateLocals() {
        updatePlayers();
        updateObjects();
    }

    @Override
    public void onPlayerMovedTile() {
        pruneLocals();
        updateLocals();
    }

    @Override
    public void join() {
        updateLocals();
    }

    @Override
    public void handle(AbstractPacket packet) {

    }

    @Override
    public void leave() {

    }

}
