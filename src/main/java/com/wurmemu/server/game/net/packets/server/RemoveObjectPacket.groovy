package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_REMOVE_OBJECT)
class RemoveObjectPacket extends MessagePacket {

    long objectID

    @Override
    encode(ByteBuf out) {
        out.writeLong(objectID)
    }

    static decode(ByteBuf frame) {
        def objectID = frame.readLong()
        new RemoveObjectPacket(objectID: objectID)
    }

}
