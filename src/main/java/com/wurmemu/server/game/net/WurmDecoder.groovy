package com.wurmemu.server.game.net

import com.wurmemu.server.game.net.packets.PacketRegistry
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
                out.add(PacketRegistry.instance.decode(type, byteBuf.readBytes(size - 1)))
                reset()
        }
    }

    void reset() {
        state(DecoderState.PAYLOAD_LENGTH)
    }

    enum DecoderState {
        PAYLOAD_LENGTH,
        PACKET_TYPE,
        PAYLOAD
    }

}
