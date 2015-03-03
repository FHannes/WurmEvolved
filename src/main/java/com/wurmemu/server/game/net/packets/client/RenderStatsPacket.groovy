package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class RenderStatsPacket extends Packet {

    int fps
    int measurements

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_RENDER_STATS)
        out.writeInt(fps)
        out.writeInt(measurements)
    }

    static RenderStatsPacket decode(ByteBuf frame) {
        def fps = frame.readInt()
        def measurements = frame.readInt()
        new RenderStatsPacket(fps: fps, measurements: measurements)
    }

}
