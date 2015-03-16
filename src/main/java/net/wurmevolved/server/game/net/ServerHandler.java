package net.wurmevolved.server.game.net;

import net.wurmevolved.common.constants.ChatColor;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.logic.MovementHandler;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.UnknownPacket;
import net.wurmevolved.server.game.net.packets.client.ClientMessagePacket;
import net.wurmevolved.server.game.net.packets.client.LoginPacket;
import net.wurmevolved.server.game.net.packets.client.MovementPacket;
import net.wurmevolved.server.game.net.packets.client.ToggleButtonPacket;
import net.wurmevolved.server.game.net.packets.server.LoginResponsePacket;
import net.wurmevolved.server.game.net.packets.server.ServerMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class ServerHandler extends SimpleChannelInboundHandler<AbstractPacket> {

    private ChannelGroup channelGroup;
    private World world;

    private Player player;
    private MovementHandler movementHandler;

    public ServerHandler(ChannelGroup channelGroup, World world) {
        this.channelGroup = channelGroup;
        this.world = world;
    }

    public void init(Player player) {
        this.player = player;
        movementHandler = new MovementHandler(world, player);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket msg) throws Exception {
        if (player == null) {
            if (msg instanceof LoginPacket) {
                LoginPacket msgLogin = (LoginPacket) msg;
                init(world.getPlayers().load(msgLogin.getUsername()));
                world.updatePosition(player.getPos());
                player.setChannel(ctx.channel());
                player.send(new LoginResponsePacket(
                        true, "Welcome to WurmEvolved", player.getPos(), player.getModel(), player.getType(),
                        player.getFaceStyle(), player.getKingdom()));
                movementHandler.initLocal();
                movementHandler.update();
                ServerMessagePacket packet = new ServerMessagePacket(
                        ":Event", String.format("%s has joined the game!", player.getUsername()), ChatColor.GREEN);
                for (Player localPlayer : world.getPlayers().getLocal(player.getPos())) {
                    if (localPlayer.equals(player)) {
                        continue;
                    }
                    localPlayer.send(packet);
                }
            }
        } else {
            if (msg instanceof ClientMessagePacket) {
                ClientMessagePacket msgMsg = (ClientMessagePacket) msg;
                ServerMessagePacket packet = new ServerMessagePacket(msgMsg.getChannel(),
                        String.format("<%s> %s", player.getUsername(), msgMsg.getMessage()),
                        ChatColor.WHITE);
                if (((ClientMessagePacket) msg).getChannel().equals(":Local")) {
                    for (Player player : world.getPlayers().getLocal(this.player.getPos())) {
                        player.send(packet);
                    }
                }
            } else if (msg instanceof MovementPacket) {
                movementHandler.handle((MovementPacket) msg);
            } else if (msg instanceof ToggleButtonPacket) {
                ToggleButtonPacket msgToggle = (ToggleButtonPacket) msg;
                System.out.println(String.format("Toggle button #%d: %s", msgToggle.getButtonID(), Boolean.toString(msgToggle.isToggleOn())));
            }
        }
        if (msg instanceof UnknownPacket) {
            System.out.println(String.format("Unknown packet with ID %d", ((UnknownPacket) msg).getType()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (movementHandler != null) {
            movementHandler.leaveWorld();
        }

        super.channelInactive(ctx);
    }

}
