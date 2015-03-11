package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_STACK_TRACE)
public class StackTracePacket extends AbstractPacket {

    private String trace;

    public StackTracePacket(String trace) {
        this.trace = trace;
    }

    @Override
    public void encode(ByteBuf out) {
        writeLongString(out, getTrace());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        String trace = readLongString(frame);
        return new StackTracePacket(trace);
    }

    public String getTrace() {
        return trace;
    }

}
