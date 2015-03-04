package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_REMOVE_CREATURE)
class RemoveCreaturePacket extends MessagePacket {

    long creatureID

    @Override
    encode(ByteBuf out) {
        out.writeLong(creatureID)
    }

    static decode(ByteBuf frame) {
        def creatureID = frame.readLong()
        new RemoveCreaturePacket(creatureID: creatureID)
    }

}
