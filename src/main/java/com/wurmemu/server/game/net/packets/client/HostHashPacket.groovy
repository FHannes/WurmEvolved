package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class HostHashPacket extends Packet {

    int hash

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_HOST_HASH)
        out.writeInt(hash)
    }

    static HostHashPacket decode(ByteBuf frame) {
        def hash = frame.readInt()
        new HostHashPacket(hash: hash)
    }

}
