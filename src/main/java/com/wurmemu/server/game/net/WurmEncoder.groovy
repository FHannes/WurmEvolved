package com.wurmemu.server.game.net

import com.wurmemu.server.game.net.packets.AbstractPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class WurmEncoder extends MessageToByteEncoder<AbstractPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
        out.writeShort(0)
        out.writeByte(msg.packetID)
        msg.encode(out)
        out.setShort(0, out.readableBytes() - 2)
    }

}
