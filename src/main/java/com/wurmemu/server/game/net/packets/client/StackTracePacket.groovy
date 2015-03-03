package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_HOST_HASH)
class StackTracePacket extends AbstractPacket {

    String trace

    @Override
    void encode(ByteBuf out) {
        writeLongString(out, trace)
    }

    static StackTracePacket decode(ByteBuf frame) {
        def trace = readLongString(frame)
        new StackTracePacket(trace: trace)
    }

}
