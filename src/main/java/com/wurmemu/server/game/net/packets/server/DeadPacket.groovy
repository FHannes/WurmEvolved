package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_DEAD)
class DeadPacket extends AbstractPacket {

    @Override
    encode(ByteBuf out) {

    }

    static decode(ByteBuf frame) {
        new DeadPacket()
    }

}
