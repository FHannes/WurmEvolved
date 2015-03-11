package com.wurmemu.server.game.net;

import com.wurmemu.common.constants.ChatColor;
import com.wurmemu.server.game.World;
import com.wurmemu.server.game.logic.PlayerHandler;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.UnknownPacket;
import com.wurmemu.server.game.net.packets.client.ClientMessagePacket;
import com.wurmemu.server.game.net.packets.client.LoginPacket;
import com.wurmemu.server.game.net.packets.client.MovementPacket;
import com.wurmemu.server.game.net.packets.client.ToggleButtonPacket;
import com.wurmemu.server.game.net.packets.server.ServerMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class ServerHandler extends SimpleChannelInboundHandler<AbstractPacket> {

    private ChannelGroup channelGroup;
    private World world;

    private PlayerHandler playerHandler;

    public ServerHandler(ChannelGroup channelGroup, World world) {
        this.channelGroup = channelGroup;
        this.world = world;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket msg) throws Exception {
        if (playerHandler == null) {
            if (msg instanceof LoginPacket) {
                world.addPlayer(playerHandler = new PlayerHandler(ctx.channel(), ((LoginPacket) msg).getUsername(), ((LoginPacket) msg).isDeveloper()));
                playerHandler.login();
            }
        } else {
            if (msg instanceof ClientMessagePacket) {
                ClientMessagePacket msgMsg = (ClientMessagePacket) msg;
                ServerMessagePacket packet = new ServerMessagePacket(msgMsg.getChannel(),
                        String.format("<%s> %s", playerHandler.getPlayer().getUsername(), msgMsg.getMessage()),
                        ChatColor.WHITE);
                if (((ClientMessagePacket) msg).getChannel().equals(":Local")) {
                    for (PlayerHandler playerHandler : world.getPlayersInLocal(this.playerHandler.getPlayer().getPos())) {
                        playerHandler.send(packet);
                    }
                }
            } else if (msg instanceof MovementPacket) {
                MovementPacket msgMove = (MovementPacket) msg;
                playerHandler.move(msgMove.getX() / 4, msgMove.getY() / 4, msgMove.getZ(), msgMove.getLayer());
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
        if (playerHandler != null) {
            world.removePlayer(playerHandler);
        }

        super.channelInactive(ctx);
    }

}
