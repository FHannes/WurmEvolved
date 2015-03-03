package com.wurmemu.server.game.net.packets

import io.netty.buffer.ByteBuf
import org.reflections.Reflections

import java.lang.reflect.Method

class PacketRegistry {

    private static instance

    private Map<Integer, Class<? extends AbstractPacket>> packets = new HashMap<>()

    PacketRegistry() {
        Reflections reflections = new Reflections(getClass().getPackage().getName() + ".client");
        reflections.getTypesAnnotatedWith(Packet.class).each { classType ->
            if (AbstractPacket.class.isAssignableFrom(classType)) {
                def packetID = AbstractPacket.getPacketID(classType)
                if (packetID != 0) {
                    packets.put(packetID, (Class<? extends AbstractPacket>) classType)
                }
            }
        }
    }

    static getInstance() {
        instance != null ? instance : (instance = new PacketRegistry())
    }

    void register(int type, Class<? extends AbstractPacket> packet) {
        type = (type + 256) % 256
        packets.put(type, packet)
    }

    AbstractPacket decode(int type, ByteBuf frame) {
        type = (type + 256) % 256
        Class<? extends AbstractPacket> clazz = packets.get(type)
        if (clazz != null) {
            Method method = clazz.getDeclaredMethod("decode", ByteBuf.class)
            if (method != null) {
                return (AbstractPacket) method.invoke(null, frame)
            }
        }
        return new UnknownPacket(type: type, frame: frame)
    }

}
