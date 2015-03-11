package com.wurmemu.server.game.net;

import com.wurmemu.common.constants.ChatColor;
import com.wurmemu.server.game.World;
import com.wurmemu.server.game.data.Player;
import com.wurmemu.server.game.logic.MovementHandler;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.UnknownPacket;
import com.wurmemu.server.game.net.packets.client.ClientMessagePacket;
import com.wurmemu.server.game.net.packets.client.LoginPacket;
import com.wurmemu.server.game.net.packets.client.MovementPacket;
import com.wurmemu.server.game.net.packets.client.ToggleButtonPacket;
import com.wurmemu.server.game.net.packets.server.LoginResponsePacket;
import com.wurmemu.server.game.net.packets.server.ServerMessagePacket;
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
                player.setDeveloper(msgLogin.isDeveloper());
                player.setChannel(ctx.channel());
                player.send(new LoginResponsePacket(
                        true, "Welcome to WurmEvolved", player.getPos(), player.isDeveloper()));
                movementHandler.initLocal();
                movementHandler.update();
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
        if (player != null) {
            world.getPlayers().remove(player);
        }

        super.channelInactive(ctx);
    }

}
