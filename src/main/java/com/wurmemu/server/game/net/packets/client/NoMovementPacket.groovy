package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class NoMovementPacket extends Packet {

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_NO_MOVEMENT)
    }

    static NoMovementPacket decode(ByteBuf frame) {
        new NoMovementPacket()
    }

}
