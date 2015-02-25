package com.wurmemu.server.game.net.packets

import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class UnknownPacket extends Packet {

    byte type
    ByteBuf frame

}
