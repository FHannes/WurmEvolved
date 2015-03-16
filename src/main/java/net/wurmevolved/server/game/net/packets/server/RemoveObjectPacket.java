package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_REMOVE_OBJECT)
public class RemoveObjectPacket extends AbstractPacket {

    private long objectID;

    public RemoveObjectPacket(long objectID) {
        this.objectID = objectID;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getObjectID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long objectID = frame.readLong();
        return new RemoveObjectPacket(objectID);
    }

    public long getObjectID() {
        return objectID;
    }

}
