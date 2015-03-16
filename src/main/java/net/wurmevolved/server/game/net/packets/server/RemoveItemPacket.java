package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_REMOVE_ITEM)
public class RemoveItemPacket extends AbstractPacket {

    private long containerID;
    private long itemID;

    public RemoveItemPacket(long containerID, long itemID) {
        this.containerID = containerID;
        this.itemID = itemID;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getContainerID());
        out.writeLong(getItemID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long containerID = frame.readLong();
        long itemID = frame.readLong();
        return new RemoveItemPacket(containerID, itemID);
    }

    public long getContainerID() {
        return containerID;
    }

    public long getItemID() {
        return itemID;
    }

}
