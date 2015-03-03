package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_TELEPORT)
class TeleportConfirmationPacket extends AbstractPacket {

    int teleportID

    @Override
    void encode(ByteBuf out) {
        out.writeInt(teleportID)
    }

    static TeleportConfirmationPacket decode(ByteBuf frame) {
        def teleportID = frame.readInt()
        new TeleportConfirmationPacket(teleportID: teleportID)
    }

}
