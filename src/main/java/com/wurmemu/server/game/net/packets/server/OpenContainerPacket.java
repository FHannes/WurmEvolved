package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_OPEN_CONTAINER)
public class OpenContainerPacket extends AbstractPacket {

    private long id;
    private String name;

    public OpenContainerPacket(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getId());
        writeString(out, getName());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long id = frame.readLong();
        String name = readString(frame);
        return new OpenContainerPacket(id, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}