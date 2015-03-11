package com.wurmemu.server.game.net.packets;

import io.netty.buffer.ByteBuf;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {

    private static PacketRegistry instance;

    private Map<Integer, Class<? extends AbstractPacket>> packets = new HashMap<>();

    PacketRegistry() {
        Reflections reflections = new Reflections(getClass().getPackage().getName() + ".client");
        for (Class<?> classType : reflections.getTypesAnnotatedWith(Packet.class)) {
            if (AbstractPacket.class.isAssignableFrom(classType)) {
                int packetID = AbstractPacket.getPacketID(classType);
                if (packetID != 0) {
                    register(packetID, (Class<? extends AbstractPacket>) classType);
                }
            }
        }
    }

    public static PacketRegistry getInstance() {
        return instance != null ? instance : (instance = new PacketRegistry());
    }

    private void register(int type, Class<? extends AbstractPacket> packet) {
        packets.put(type, packet);
    }

    public AbstractPacket decode(int type, ByteBuf frame) {
        Class<? extends AbstractPacket> clazz = packets.get(type);
        if (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod("decode", ByteBuf.class);
                if (method != null) {
                    return (AbstractPacket) method.invoke(null, frame);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new UnknownPacket(type, frame);
    }

}
