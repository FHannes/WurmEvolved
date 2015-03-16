package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_CHANGE_MODEL)
public class ChangeModelPacket extends AbstractPacket {

    private long targetID;
    private String modelName;

    public ChangeModelPacket(long targetID, String modelName) {
        this.targetID = targetID;
        this.modelName = modelName;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getTargetID());
        writeString(out, getModelName());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long targetID = frame.readLong();
        String modelName = readString(frame);
        return new ChangeModelPacket(targetID, modelName);
    }

    public long getTargetID() {
        return targetID;
    }

    public String getModelName() {
        return modelName;
    }

}
