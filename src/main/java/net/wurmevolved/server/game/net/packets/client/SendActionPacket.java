package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.constants.ActionType;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_SEND_ACTION)
public class SendActionPacket extends AbstractPacket {

    private long[] targetIDs;
    private long activeID;
    private ActionType actionType;

    public SendActionPacket(long[] targetIDs, long activeID, ActionType actionType) {
        this.targetIDs = targetIDs;
        this.activeID = activeID;
        this.actionType = actionType;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeShort(getTargetIDs().length);
        out.writeLong(getActiveID());
        for (long targetID : getTargetIDs()) {
            out.writeLong(targetID);
        }
        out.writeShort(getActionType().getId());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long[] targetIDs = new long[frame.readShort()];
        long activeID = frame.readLong();
        for (int idx = 0; idx < targetIDs.length; idx++) {
            targetIDs[idx] = frame.readLong();
        }
        ActionType actionType = ActionType.get(frame.readShort());
        return new SendActionPacket(targetIDs, activeID, actionType);
    }

    public long[] getTargetIDs() {
        return targetIDs;
    }

    public long getActiveID() {
        return activeID;
    }

    public ActionType getActionType() {
        return actionType;
    }

}
