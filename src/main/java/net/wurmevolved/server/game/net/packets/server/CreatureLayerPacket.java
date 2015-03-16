package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_CREATURE_LAYER)
public class CreatureLayerPacket extends AbstractPacket {

    private long creatureID;
    private byte layer;

    public CreatureLayerPacket(long creatureID, byte layer) {
        this.creatureID = creatureID;
        this.layer = layer;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getCreatureID());
        out.writeByte(getLayer());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long creatureID = frame.readLong();
        byte layer = frame.readByte();
        return new CreatureLayerPacket(creatureID, layer);
    }

    public long getCreatureID() {
        return creatureID;
    }

    public byte getLayer() {
        return layer;
    }

}
