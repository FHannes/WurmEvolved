package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_MOVEMENT)
public class Movement2DPacket extends AbstractPacket {

    private long creatureID;
    private float xOffset;
    private float yOffset;
    private float rot;

    public Movement2DPacket(long creatureID, float xOffset, float yOffset, float rot) {
        this.creatureID = creatureID;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.rot = rot;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getCreatureID());
        out.writeByte((byte) Math.round(getYOffset() * 40F));
        out.writeByte((byte) Math.round(getXOffset() * 40F));
        out.writeByte((byte) (getRot() / 360F * 256F));
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long creatureID = frame.readLong();
        float yOffset = frame.readByte() / 40F;
        float xOffset = frame.readByte() / 40F;
        float rot = frame.readByte() / 256F * 360F;
        return new Movement2DPacket(creatureID, xOffset, yOffset, rot);
    }

    public long getCreatureID() {
        return creatureID;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public float getRot() {
        return rot;
    }

}
