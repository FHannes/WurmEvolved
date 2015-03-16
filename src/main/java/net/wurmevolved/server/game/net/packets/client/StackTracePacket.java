package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

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
