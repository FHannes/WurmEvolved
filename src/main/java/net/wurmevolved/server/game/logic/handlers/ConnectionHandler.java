package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.server.AddUserPacket;
import net.wurmevolved.server.game.net.packets.server.RemoveCreaturePacket;
import net.wurmevolved.server.game.net.packets.server.RemoveUserPacket;

public class ConnectionHandler extends LogicHandler {

    private World world;
    private Player player;

    public ConnectionHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    @Override
    public void join() {
        world.getPlayers().all().forEach(worldPlayer -> {
            if (worldPlayer.equals(player)) {
                return;
            }
            player.send(new AddUserPacket(":Server", worldPlayer.getUsername(), worldPlayer.getId()));
        });
        world.getPlayers().broadcast(new AddUserPacket(":Server", player.getUsername(), player.getId()));
    }

    @Override
    public void handle(AbstractPacket packet) {

    }

    @Override
    public void leave() {
        world.getPlayers().remove(player);
        RemoveCreaturePacket packetRemove = new RemoveCreaturePacket(player.getId());
        world.getPlayers().all().forEach(worldPlayer -> {
            if (worldPlayer.hasLocal(player)) {
                worldPlayer.removeLocal(player);
                worldPlayer.send(packetRemove);
            }
        });
        RemoveUserPacket packetRemoveUser = new RemoveUserPacket(":Local", player.getUsername());
        player.getLocal(Player.class).forEach(p -> p.send(packetRemoveUser));
        world.getPlayers().broadcast(new RemoveUserPacket(":Server", player.getUsername()));
        world.getPlayers().save(player);
    }

}
