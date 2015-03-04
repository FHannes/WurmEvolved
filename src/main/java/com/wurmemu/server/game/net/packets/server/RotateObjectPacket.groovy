package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_ROTATE_OBJECT)
class RotateObjectPacket extends MessagePacket {

    long objectID
    float angle

    @Override
    encode(ByteBuf out) {
        out.writeLong(objectID)
        out.writeFloat(angle)
    }

    static decode(ByteBuf frame) {
        def objectID = frame.readLong()
        def angle = frame.readFloat()
        new RotateObjectPacket(objectID: objectID, angle: angle)
    }

}
