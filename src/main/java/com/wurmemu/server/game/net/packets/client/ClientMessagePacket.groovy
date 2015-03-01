package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import com.wurmemu.server.game.net.packets.MessagePacket
import io.netty.buffer.ByteBuf

class ClientMessagePacket extends MessagePacket {

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_MESSAGE)
        writeString(out, message)
        writeString(out, channel)
    }

    static ClientMessagePacket decode(ByteBuf frame) {
        def message = readString(frame)
        def channel = readString(frame)
        new ClientMessagePacket(channel: channel, message: message)
    }

}
