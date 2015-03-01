package com.wurmemu.server.game.net

import com.wurmemu.common.constants.ChatColor
import com.wurmemu.server.game.World
import com.wurmemu.server.game.net.packets.client.ClientMessagePacket
import com.wurmemu.server.game.net.packets.client.LoginPacket
import com.wurmemu.server.game.net.packets.server.LoginResponsePacket
import com.wurmemu.server.game.net.packets.UnknownPacket
import com.wurmemu.server.game.net.packets.server.ServerMessagePacket
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.ChannelGroup

import java.awt.Color

class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    ChannelGroup channelGroup
    World world

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        if (msg instanceof LoginPacket) {
            ctx.channel().write(new LoginResponsePacket(
                    allowLogin: true, reason: "Test", layer: 0, developer: false))
        } else if (msg instanceof ClientMessagePacket) {
            println("Message from client to channel '${msg.channel}': ${msg.message}")
            ctx.channel().write(new ServerMessagePacket(
                    channel: msg.channel, message: "Echo: ${msg.message}", color: ChatColor.WHITE))
        } else if (msg instanceof UnknownPacket) {
            println "Unknown packet with ID ${msg.type}"
        }
    }

}
