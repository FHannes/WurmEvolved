package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class ClientMessagePacket extends Packet {

    String channel
    String message

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_CLIENT_MESSAGE)
        writeString(out, channel)
        writeString(out, message)
    }

    static ClientMessagePacket decode(ByteBuf frame) {
        def message = readString(frame)
        def channel = readString(frame)
        new ClientMessagePacket(channel: channel, message: message)
    }

}
