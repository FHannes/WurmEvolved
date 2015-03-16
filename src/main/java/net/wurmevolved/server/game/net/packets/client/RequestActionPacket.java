package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_REQUEST_ACTION)
public class RequestActionPacket extends AbstractPacket {

    private byte requestID;
    private long targetID;
    private long activeID;

    public RequestActionPacket(byte requestID, long targetID, long activeID) {
        this.requestID = requestID;
        this.targetID = targetID;
        this.activeID = activeID;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(getRequestID());
        out.writeLong(getTargetID());
        out.writeLong(getActiveID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        byte requestID = frame.readByte();
        long targetID = frame.readLong();
        long activeID = frame.readLong();
        return new RequestActionPacket(requestID, targetID, activeID);
    }

    public byte getRequestID() {
        return requestID;
    }

    public long getTargetID() {
        return targetID;
    }

    public long getActiveID() {
        return activeID;
    }

}
