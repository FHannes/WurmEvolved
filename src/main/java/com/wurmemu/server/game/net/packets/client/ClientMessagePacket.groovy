package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_MESSAGE)
class ClientMessagePacket extends MessagePacket {

    @Override
    encode(ByteBuf out) {
        writeString(out, message)
        writeString(out, channel)
    }

    static decode(ByteBuf frame) {
        def message = readString(frame)
        def channel = readString(frame)
        new ClientMessagePacket(channel: channel, message: message)
    }

}
