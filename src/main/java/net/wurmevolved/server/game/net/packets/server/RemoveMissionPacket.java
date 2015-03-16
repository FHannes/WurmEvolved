package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_REMOVE_MISSION)
public class RemoveMissionPacket extends AbstractPacket {

    private long missionID;

    public RemoveMissionPacket(long missionID) {
        this.missionID = missionID;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getMissionID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long missionID = frame.readLong();
        return new RemoveMissionPacket(missionID);
    }

    public long getMissionID() {
        return missionID;
    }

}
