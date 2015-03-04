package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

import java.awt.*

@Packet(Protocol.PACKET_REMOVE_ITEM)
class RemoveItemPacket extends MessagePacket {

    long containerID
    long itemID

    @Override
    encode(ByteBuf out) {
        out.writeLong(containerID)
        out.writeLong(itemID)
    }

    static decode(ByteBuf frame) {
        def containerID = frame.readLong()
        def itemID = frame.readLong()
        new RemoveItemPacket(containerID: containerID, itemID: itemID)
    }

}
