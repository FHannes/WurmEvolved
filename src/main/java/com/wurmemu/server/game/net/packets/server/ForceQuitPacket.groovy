package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_FORCE_QUIT)
class ForceQuitPacket extends AbstractPacket {

    String reason
    boolean confirm

    @Override
    encode(ByteBuf out) {
        if (reason != null && !reason.equals("")) {
            writeLongString(out, reason)
        }
        out.writeBoolean(confirm)
    }

}
