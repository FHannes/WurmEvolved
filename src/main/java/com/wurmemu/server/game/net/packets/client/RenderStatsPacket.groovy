package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_RENDER_STATS)
class RenderStatsPacket extends AbstractPacket {

    int fps
    int measurements

    @Override
    encode(ByteBuf out) {
        out.writeInt(fps)
        out.writeInt(measurements)
    }

    static decode(ByteBuf frame) {
        def fps = frame.readInt()
        def measurements = frame.readInt()
        new RenderStatsPacket(fps: fps, measurements: measurements)
    }

}
