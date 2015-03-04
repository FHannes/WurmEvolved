package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_STUNNED)
class StunnedPacket extends AbstractPacket {

    boolean stunned

    @Override
    encode(ByteBuf out) {
        out.writeBoolean(stunned)
    }

    static decode(ByteBuf frame) {
        def stunned = frame.readBoolean()
        new StunnedPacket(stunned: stunned)
    }

}
