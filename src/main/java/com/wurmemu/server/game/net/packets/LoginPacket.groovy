package com.wurmemu.server.game.net.packets

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class LoginPacket extends Packet {

    int protocol
    String username
    boolean developer

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_LOGIN)
        out.writeInt(protocol)
        writeString(out, username)
        writeString(out, "${username},${developer ? "true" : "false"}")
    }

    static LoginPacket decode(ByteBuf frame) {
        def protocol = frame.readInt()
        def username = readString(frame)
        def data = readString(frame).split(',')
        new LoginPacket(protocol: protocol, username: username, developer: data[1])
    }

}
