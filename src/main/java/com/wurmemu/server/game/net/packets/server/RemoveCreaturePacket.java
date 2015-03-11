package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_REMOVE_CREATURE)
public class RemoveCreaturePacket extends AbstractPacket {

    private long creatureID;

    public RemoveCreaturePacket(long creatureID) {
        this.creatureID = creatureID;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getCreatureID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long creatureID = frame.readLong();
        return new RemoveCreaturePacket(creatureID);
    }

    public long getCreatureID() {
        return creatureID;
    }

}
