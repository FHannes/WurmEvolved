package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_RENDER_STATS)
public class RenderStatsPacket extends AbstractPacket {

    private int fps;
    private int measurements;

    public RenderStatsPacket(int fps, int measurements) {
        this.fps = fps;
        this.measurements = measurements;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeInt(getFps());
        out.writeInt(getMeasurements());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        int fps = frame.readInt();
        int measurements = frame.readInt();
        return new RenderStatsPacket(fps, measurements);
    }

    public int getFps() {
        return fps;
    }

    public int getMeasurements() {
        return measurements;
    }

}
