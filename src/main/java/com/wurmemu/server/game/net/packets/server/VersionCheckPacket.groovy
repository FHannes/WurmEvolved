package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_VERSION_CHECK)
class VersionCheckPacket extends AbstractPacket {

    @Override
    encode(ByteBuf out) {

    }

    static decode(ByteBuf frame) {
        new VersionCheckPacket()
    }

}
