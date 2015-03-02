package com.wurmemu.server.game.net

import io.netty.buffer.ByteBuf

abstract class Packet {

    abstract void encode(ByteBuf out)

    static String readString(ByteBuf byteBuf) {
        new String(byteBuf.readBytes(byteBuf.readByte()).array())
    }

    static String readLongString(ByteBuf byteBuf) {
        new String(byteBuf.readBytes(byteBuf.readShort()).array())
    }

    static void writeString(ByteBuf byteBuf, String str) {
        byteBuf.writeByte(str.length())
        byteBuf.writeBytes(str.getBytes())
    }

    static void writeLongString(ByteBuf byteBuf, String str) {
        byteBuf.writeShort(str.length())
        byteBuf.writeBytes(str.getBytes())
    }

}
