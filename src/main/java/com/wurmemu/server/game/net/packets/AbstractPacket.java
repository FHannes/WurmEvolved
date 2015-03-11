package com.wurmemu.server.game.net.packets;

import io.netty.buffer.ByteBuf;

import java.lang.annotation.Annotation;

public abstract class AbstractPacket {

    public abstract void encode(ByteBuf out);

    public int getPacketID() {
        return getPacketID(getClass());
    }

    public static int getPacketID(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Packet.class);
        if (annotation != null) {
            return ((Packet) annotation).value();
        }
        return 0;
    }

    public static String readString(ByteBuf byteBuf) {
        return new String(byteBuf.readBytes(byteBuf.readByte()).array());
    }

    public static String readLongString(ByteBuf byteBuf) {
        return new String(byteBuf.readBytes(byteBuf.readShort()).array());
    }

    public static void writeString(ByteBuf byteBuf, String str) {
        byteBuf.writeByte(str.length());
        byteBuf.writeBytes(str.getBytes());
    }

    public static void writeLongString(ByteBuf byteBuf, String str) {
        byteBuf.writeShort(str.length());
        byteBuf.writeBytes(str.getBytes());
    }

}
