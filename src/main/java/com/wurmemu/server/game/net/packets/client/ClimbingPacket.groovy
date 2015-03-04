package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_CLIMBING)
class ClimbingPacket extends AbstractPacket {

    boolean enable

    @Override
    encode(ByteBuf out) {
        out.writeBoolean(enable)
    }

    static decode(ByteBuf frame) {
        def enable = frame.readBoolean()
        new ClimbingPacket(enable: enable)
    }

}
