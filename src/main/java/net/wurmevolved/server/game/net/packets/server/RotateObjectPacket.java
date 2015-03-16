package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_ROTATE_OBJECT)
class RotateObjectPacket extends AbstractPacket {

    private long objectID;
    private float angle;

    public RotateObjectPacket(long objectID, float angle) {
        this.objectID = objectID;
        this.angle = angle;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getObjectID());
        out.writeFloat(getAngle());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long objectID = frame.readLong();
        float angle = frame.readFloat();
        return new RotateObjectPacket(objectID, angle);
    }

    public long getObjectID() {
        return objectID;
    }

    public float getAngle() {
        return angle;
    }

}
