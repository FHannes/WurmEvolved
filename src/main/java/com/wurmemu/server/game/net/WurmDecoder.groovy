package com.wurmemu.server.game.net

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.LoginPacket
import com.wurmemu.server.game.net.packets.UnknownPacket
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ReplayingDecoder

class WurmDecoder extends ReplayingDecoder<DecoderState> {

    short size
    byte type

    WurmDecoder() {
        super(DecoderState.PAYLOAD_LENGTH)
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch (state()) {
            case DecoderState.PAYLOAD_LENGTH:
                size = byteBuf.readShort()
                checkpoint(DecoderState.PACKET_TYPE)
            case DecoderState.PACKET_TYPE:
                type = byteBuf.readByte()
                checkpoint(DecoderState.PAYLOAD)
            case DecoderState.PAYLOAD:
                Packet packet
                ByteBuf frame = byteBuf.readBytes(size - 1)
                switch (type) {
                    case Protocol.PACKET_LOGIN:
                        def protocol = frame.readInt()
                        def username = readString(frame)
                        def data = readString(frame).split(',')
                        packet = new LoginPacket(
                                protocol: protocol,
                                username: username,
                                developer: data[1])
                        break;
                    default:
                        packet = new UnknownPacket(type: type, frame: frame)
                }
                out.add(packet)
        }
    }

    static String readString(ByteBuf byteBuf) {
        new String(byteBuf.readBytes(byteBuf.readByte()).array())
    }

    enum DecoderState {
        PAYLOAD_LENGTH,
        PACKET_TYPE,
        PAYLOAD
    }

}
