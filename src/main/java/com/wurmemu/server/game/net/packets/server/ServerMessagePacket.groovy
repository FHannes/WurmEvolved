package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import io.netty.buffer.ByteBuf

import java.awt.Color

class ServerMessagePacket extends MessagePacket {

    Color color

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_MESSAGE)
        writeString(out, message)
        out.writeByte(color.red)
        out.writeByte(color.green)
        out.writeByte(color.blue)
        writeString(out, channel)
    }

    static ServerMessagePacket decode(ByteBuf frame) {
        def message = readString(frame)
        def r = frame.readByte()
        def g = frame.readByte()
        def b = frame.readByte()
        def channel = readString(frame)
        new ServerMessagePacket(channel: channel, message: message, color: new Color(r, g, b))
    }

}
