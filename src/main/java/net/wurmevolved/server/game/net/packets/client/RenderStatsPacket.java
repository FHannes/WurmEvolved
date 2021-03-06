package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

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
