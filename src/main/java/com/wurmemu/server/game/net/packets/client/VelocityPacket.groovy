package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_VELOCITY)
class VelocityPacket extends AbstractPacket {

    float velocity

    @Override
    encode(ByteBuf out) {
        out.writeFloat(velocity)
    }

    static decode(ByteBuf frame) {
        def velocity = frame.readFloat()
        new VelocityPacket(velocity: velocity)
    }

}
