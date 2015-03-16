package net.wurmevolved.server.game.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.wurmevolved.server.game.net.packets.AbstractPacket;

public class WurmEncoder extends MessageToByteEncoder<AbstractPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
        out.writeShort(0);
        out.writeByte(msg.getPacketID());
        msg.encode(out);
        out.setShort(0, out.readableBytes() - 2);
    }

}
