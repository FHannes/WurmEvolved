package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_STUNNED)
public class StunnedPacket extends AbstractPacket {

    private boolean stunned;

    public StunnedPacket(boolean stunned) {
        this.stunned = stunned;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeBoolean(isStunned());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        boolean stunned = frame.readBoolean();
        return new StunnedPacket(stunned);
    }

    public boolean isStunned() {
        return stunned;
    }

}
