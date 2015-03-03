package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_LOGIN)
class LoginPacket extends AbstractPacket {

    int protocol
    String username
    boolean developer

    @Override
    void encode(ByteBuf out) {
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
