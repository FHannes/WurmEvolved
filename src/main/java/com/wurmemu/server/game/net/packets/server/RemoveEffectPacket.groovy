package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_REMOVE_EFFECT)
class RemoveEffectPacket extends MessagePacket {

    long targetID
    byte effect

    @Override
    encode(ByteBuf out) {
        out.writeLong(targetID)
        out.writeByte(effect)
    }

    static decode(ByteBuf frame) {
        def targetID = frame.readLong()
        def effect = frame.readByte()
        new RemoveEffectPacket(targetID: targetID, effect: effect)
    }

}
