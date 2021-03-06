package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_MOVEMENT_3D)
public class Movement3DPacket extends AbstractPacket {

    private long creatureID;
    private float xOffset;
    private float yOffset;
    private float z;
    private float rot;

    public Movement3DPacket(long creatureID, float xOffset, float yOffset, float z, float rot) {
        this.creatureID = creatureID;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.z = z;
        this.rot = rot;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getCreatureID());
        out.writeShort((short) (getZ() * 10F));
        out.writeByte((byte) Math.round(getXOffset() * 40F));
        out.writeByte((byte) (getRot() / 360F * 256F));
        out.writeByte((byte) Math.round(getYOffset() * 40F));
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long creatureID = frame.readLong();
        float z = frame.readShort() / 10F;
        float xOffset = frame.readByte() / 40F;
        float rot = frame.readByte() / 256F * 360F;
        float yOffset = frame.readByte() / 40F;
        return new Movement3DPacket(creatureID, xOffset, yOffset, z, rot);
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

    public float getZ() {
        return z;
    }

    public float getRot() {
        return rot;
    }

}
