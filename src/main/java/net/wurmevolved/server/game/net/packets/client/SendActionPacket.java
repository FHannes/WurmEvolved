package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.constants.Action;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_SEND_ACTION)
public class SendActionPacket extends AbstractPacket {

    private long[] targetIDs;
    private long activeID;
    private Action action;

    public SendActionPacket(long[] targetIDs, long activeID, Action action) {
        this.targetIDs = targetIDs;
        this.activeID = activeID;
        this.action = action;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeShort(getTargetIDs().length);
        out.writeLong(getActiveID());
        for (long targetID : getTargetIDs()) {
            out.writeLong(targetID);
        }
        out.writeShort(getAction().getId());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long[] targetIDs = new long[frame.readShort()];
        long activeID = frame.readLong();
        for (int idx = 0; idx < targetIDs.length; idx++) {
            targetIDs[idx] = frame.readLong();
        }
        Action action = Action.get(frame.readShort());
        return new SendActionPacket(targetIDs, activeID, action);
    }

    public long[] getTargetIDs() {
        return targetIDs;
    }

    public long getActiveID() {
        return activeID;
    }

    public Action getAction() {
        return action;
    }

}
