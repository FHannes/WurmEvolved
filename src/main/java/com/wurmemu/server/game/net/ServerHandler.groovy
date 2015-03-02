package com.wurmemu.server.game.net

import com.wurmemu.common.constants.ChatColor
import com.wurmemu.server.game.World
import com.wurmemu.server.game.logic.PlayerHandler
import com.wurmemu.server.game.map.Chunk
import com.wurmemu.server.game.net.packets.client.ClientMessagePacket
import com.wurmemu.server.game.net.packets.client.LoginPacket
import com.wurmemu.server.game.net.packets.server.LoginResponsePacket
import com.wurmemu.server.game.net.packets.UnknownPacket
import com.wurmemu.server.game.net.packets.server.ServerMessagePacket
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.ChannelGroup

class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    ChannelGroup channelGroup
    World world
    PlayerHandler player

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if (player == null) {
            if (msg instanceof LoginPacket) {
                world.addPlayer(player = new PlayerHandler(ctx.channel(), msg.username, msg.developer))
                player.login()
            }
        } else {
            if (msg instanceof ClientMessagePacket) {
                // TODO: Send message to all players in local
                player.send(new ServerMessagePacket(
                        channel: msg.channel, message: "<${player.player.username}> ${msg.message}",
                        color: ChatColor.WHITE))
            }
        }
        if (msg instanceof UnknownPacket) {
            println "Unknown packet with ID ${msg.type}"
        }
    }

    @Override
    void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel())

        super.channelActive(ctx)
    }

    @Override
    void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (player != null) {
            world.removePlayer(player)
        }

        super.channelInactive(ctx)
    }

}
