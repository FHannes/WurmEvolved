package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_NO_MOVEMENT)
class NoMovementPacket extends AbstractPacket {

    @Override
    void encode(ByteBuf out) {

    }

    static NoMovementPacket decode(ByteBuf frame) {
        new NoMovementPacket()
    }

}