package com.wurmemu.server.game.net.packets

import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

import java.lang.reflect.Method

class PacketRegistry {

    private static instance

    private Map<Integer, Class<? extends Packet>> packets = new HashMap<>()

    static getInstance() {
        instance != null ? instance : (instance = new PacketRegistry())
    }

    void register(int type, Class<? extends Packet> packet) {
        type = (type + 256) % 256
        packets.put(type, packet)
    }

    Packet decode(int type, ByteBuf frame) {
        type = (type + 256) % 256
        Class<? extends Packet> clazz = packets.get(type)
        if (clazz != null) {
            Method method = clazz.getDeclaredMethod("decode", ByteBuf.class)
            if (method != null) {
                return (Packet) method.invoke(null, frame)
            }
        }
        return new UnknownPacket(type: type, frame: frame)
    }

}
