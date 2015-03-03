package com.wurmemu.server.game.net.packets

import io.netty.buffer.ByteBuf

class UnknownPacket extends AbstractPacket {

    byte type
    ByteBuf frame

    @Override
    encode(ByteBuf out) {
        out.writeByte(type)
        out.writeBytes(frame)
    }

}
