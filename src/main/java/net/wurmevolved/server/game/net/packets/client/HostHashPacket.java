package net.wurmevolved.server.game.net.packets.client;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_HOST_HASH)
public class HostHashPacket extends AbstractPacket {

    private int hash;

    public HostHashPacket(int hash) {
        this.hash = hash;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeInt(getHash());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        int hash = frame.readInt();
        return new HostHashPacket(hash);
    }

    public int getHash() {
        return hash;
    }

}
