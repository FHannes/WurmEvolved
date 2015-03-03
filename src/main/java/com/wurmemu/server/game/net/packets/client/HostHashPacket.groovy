package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_HOST_HASH)
class HostHashPacket extends AbstractPacket {

    int hash

    @Override
    encode(ByteBuf out) {
        out.writeInt(hash)
    }

    static decode(ByteBuf frame) {
        def hash = frame.readInt()
        new HostHashPacket(hash: hash)
    }

}
