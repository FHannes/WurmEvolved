package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class StackTracePacket extends Packet {

    String trace

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_HOST_HASH)
        writeLongString(out, trace)
    }

    static StackTracePacket decode(ByteBuf frame) {
        def trace = readLongString(frame)
        new StackTracePacket(trace: trace)
    }

}
