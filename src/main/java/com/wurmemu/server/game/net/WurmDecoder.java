package com.wurmemu.server.game.net;

import com.wurmemu.server.game.net.packets.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class WurmDecoder extends ReplayingDecoder<WurmDecoder.DecoderState> {

    private short size;
    private byte type;

    public WurmDecoder() {
        super(DecoderState.PAYLOAD_LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        switch (state()) {
            case PAYLOAD_LENGTH:
                size = byteBuf.readShort();
                checkpoint(DecoderState.PACKET_TYPE);
            case PACKET_TYPE:
                type = byteBuf.readByte();
                checkpoint(DecoderState.PAYLOAD);
            case PAYLOAD:
                out.add(PacketRegistry.getInstance().decode(type, byteBuf.readBytes(size - 1)));
                reset();
        }
    }

    void reset() {
        state(DecoderState.PAYLOAD_LENGTH);
    }

    public static enum DecoderState {
        PAYLOAD_LENGTH,
        PACKET_TYPE,
        PAYLOAD
    }

}
