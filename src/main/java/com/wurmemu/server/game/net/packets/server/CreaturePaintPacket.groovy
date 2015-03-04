package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

import java.awt.Color

@Packet(Protocol.PACKET_CREATURE_PAINT)
class CreaturePaintPacket extends MessagePacket {

    long creatureID
    Color color
    byte paintMode

    @Override
    encode(ByteBuf out) {
        out.writeLong(creatureID)
        out.writeByte(color.red)
        out.writeByte(color.green)
        out.writeByte(color.blue)
        out.writeByte(color.alpha)
        out.writeByte(paintMode)
    }

    static decode(ByteBuf frame) {
        def creatureID = frame.readLong()
        def r = frame.readByte()
        def g = frame.readByte()
        def b = frame.readByte()
        def a = frame.readByte()
        def paintMode = frame.readByte()
        new CreaturePaintPacket(creatureID: creatureID, color: new Color(r, g, b, a), paintMode: paintMode)
    }

}
