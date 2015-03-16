package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_CREATURE_SCALE)
public class CreatureScalePacket extends AbstractPacket {

    private long creatureID;
    private float xScale;
    private float yScale;
    private float zScale;

    public CreatureScalePacket(long creatureID, float xScale, float yScale, float zScale) {
        this.creatureID = creatureID;
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getCreatureID());
        out.writeByte((byte) Math.floor(getXScale() * 64));
        out.writeByte((byte) Math.floor(getYScale() * 64));
        out.writeByte((byte) Math.floor(getZScale() * 64));
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long creatureID = frame.readLong();
        float xScale = frame.readByte() / 64F;
        float yScale = frame.readByte() / 64F;
        float zScale = frame.readByte() / 64F;
        return new CreatureScalePacket(creatureID, xScale, yScale, zScale);
    }

    public long getCreatureID() {
        return creatureID;
    }

    public float getXScale() {
        return xScale;
    }

    public float getYScale() {
        return yScale;
    }

    public float getZScale() {
        return zScale;
    }

}
