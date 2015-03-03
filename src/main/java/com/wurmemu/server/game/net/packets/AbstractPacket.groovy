package com.wurmemu.server.game.net.packets

import io.netty.buffer.ByteBuf

import java.lang.annotation.Annotation

abstract class AbstractPacket {

    abstract encode(ByteBuf out)

    int getPacketID() {
        return getPacketID(getClass())
    }

    static int getPacketID(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Packet.class)
        if (annotation instanceof Packet) {
            return annotation.value()
        }
        return 0
    }

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
