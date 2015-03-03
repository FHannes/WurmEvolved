package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

import java.awt.*

@Packet(Protocol.PACKET_MESSAGE)
class ServerMessagePacket extends MessagePacket {

    Color color

    @Override
    encode(ByteBuf out) {
        writeString(out, channel)
        out.writeByte(color.red)
        out.writeByte(color.green)
        out.writeByte(color.blue)
        writeLongString(out, message)
    }

    static decode(ByteBuf frame) {
        def channel = readString(frame)
        def r = frame.readByte()
        def g = frame.readByte()
        def b = frame.readByte()
        def message = readLongString(frame)
        new ServerMessagePacket(channel: channel, message: message, color: new Color(r, g, b))
    }

}
