package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_CREATURE_LAYER)
class CreatureLayerPacket extends MessagePacket {

    long creatureID
    byte layer

    @Override
    encode(ByteBuf out) {
        out.writeLong(creatureID)
        out.writeByte(layer)
    }

    static decode(ByteBuf frame) {
        def creatureID = frame.readLong()
        def layer = frame.readByte()
        new CreatureLayerPacket(creatureID: creatureID, layer: layer)
    }

}
