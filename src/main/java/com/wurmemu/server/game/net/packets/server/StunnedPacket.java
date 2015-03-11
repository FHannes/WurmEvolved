package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

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
