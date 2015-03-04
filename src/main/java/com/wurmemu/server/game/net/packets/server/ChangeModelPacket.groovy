package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_CHANGE_MODEL)
class ChangeModelPacket extends MessagePacket {

    long targetID
    String modelName

    @Override
    encode(ByteBuf out) {
        out.writeLong(targetID)
        writeString(modelName)
    }

    static decode(ByteBuf frame) {
        def targetID = frame.readLong()
        def modelName = readString(frame)
        new ChangeModelPacket(targetID: targetID, modelName: modelName)
    }

}
