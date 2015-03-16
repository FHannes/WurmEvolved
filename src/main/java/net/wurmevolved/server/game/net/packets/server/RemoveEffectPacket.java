package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_REMOVE_EFFECT)
public class RemoveEffectPacket extends AbstractPacket {

    private long targetID;
    private byte effect;

    public RemoveEffectPacket(long targetID, byte effect) {
        this.targetID = targetID;
        this.effect = effect;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getTargetID());
        out.writeByte(getEffect());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        long targetID = frame.readLong();
        byte effect = frame.readByte();
        return new RemoveEffectPacket(targetID, effect);
    }

    public long getTargetID() {
        return targetID;
    }

    public byte getEffect() {
        return effect;
    }

}
