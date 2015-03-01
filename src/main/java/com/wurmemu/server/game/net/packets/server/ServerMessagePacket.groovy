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
        writeString(out, channel)
        out.writeByte(color.red)
        out.writeByte(color.green)
        out.writeByte(color.blue)
        writeString(out, message)
    }

    static ServerMessagePacket decode(ByteBuf frame) {
        def channel = readString(frame)
        def r = frame.readByte()
        def g = frame.readByte()
        def b = frame.readByte()
        def message = readString(frame)
        new ServerMessagePacket(channel: channel, message: message, color: new Color(r, g, b))
    }

}
