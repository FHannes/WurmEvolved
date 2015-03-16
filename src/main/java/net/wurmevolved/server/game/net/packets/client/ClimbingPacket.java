package net.wurmevolved.server.game.net.packets.client;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_CLIMBING)
public class ClimbingPacket extends AbstractPacket {

    private boolean enable;

    public ClimbingPacket(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeBoolean(isEnable());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        boolean enable = frame.readBoolean();
        return new ClimbingPacket(enable);
    }

    public boolean isEnable() {
        return enable;
    }

}
