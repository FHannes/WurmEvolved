package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_CREATURE_SCALE)
class CreatureScalePacket extends MessagePacket {

    long creatureID
    float xScale
    float yScale
    float zScale

    @Override
    encode(ByteBuf out) {
        out.writeLong(creatureID)
        out.writeByte((byte) Math.floor(xScale * 64))
        out.writeByte((byte) Math.floor(yScale * 64))
        out.writeByte((byte) Math.floor(zScale * 64))
    }

    static decode(ByteBuf frame) {
        def creatureID = frame.readLong()
        def xScale = frame.readByte() / 64F
        def yScale = frame.readByte() / 64F
        def zScale = frame.readByte() / 64F
        new CreatureScalePacket(creatureID: creatureID, xScale: xScale, yScale: yScale, zScale: zScale)
    }

}
