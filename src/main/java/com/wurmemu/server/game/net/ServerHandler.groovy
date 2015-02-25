package com.wurmemu.server.game.net

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.ChannelGroup

class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    ChannelGroup channelGroup

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        println(msg.class.name)
    }

}
