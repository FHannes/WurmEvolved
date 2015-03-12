package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

import java.awt.*;

@Packet(Protocol.PACKET_CREATURE_PAINT)
public class CreaturePaintPacket extends AbstractPacket {

    private long creatureID;
    private Color color;
    private byte paintMode;

    public CreaturePaintPacket(long creatureID, Color color, byte paintMode) {
        this.creatureID = creatureID;
        this.color = color;
        this.paintMode = paintMode;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getCreatureID());
        out.writeByte(getColor().getRed());
        out.writeByte(getColor().getGreen());
        out.writeByte(getColor().getBlue());
        out.writeByte(getColor().getAlpha());
        out.writeByte(getPaintMode());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long creatureID = frame.readLong();
        int r = frame.readByte() & 0xFF;
        int g = frame.readByte() & 0xFF;
        int b = frame.readByte() & 0xFF;
        int a = frame.readByte() & 0xFF;
        byte paintMode = frame.readByte();
        return new CreaturePaintPacket(creatureID, new Color(r, g, b, a), paintMode);
    }

    public long getCreatureID() {
        return creatureID;
    }

    public Color getColor() {
        return color;
    }

    public byte getPaintMode() {
        return paintMode;
    }

}
