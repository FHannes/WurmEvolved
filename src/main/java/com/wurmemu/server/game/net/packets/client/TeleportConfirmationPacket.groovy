package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class TeleportConfirmationPacket extends Packet {

    int teleportID

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_TELEPORT)
        out.writeInt(teleportID)
    }

    static TeleportConfirmationPacket decode(ByteBuf frame) {
        def teleportID = frame.readInt()
        new TeleportConfirmationPacket(teleportID: teleportID)
    }

}
