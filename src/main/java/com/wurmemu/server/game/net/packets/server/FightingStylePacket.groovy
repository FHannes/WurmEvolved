package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_FIGHTING_STYLE)
class FightingStylePacket extends MessagePacket {

    byte style

    @Override
    encode(ByteBuf out) {
        out.writeByte(style)
    }

    static decode(ByteBuf frame) {
        def style = frame.readByte()
        new FightingStylePacket(style: style)
    }

}
