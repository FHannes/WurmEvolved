package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_VERSION_CHECK)
public class VersionCheckPacket extends AbstractPacket {

    @Override
    public void encode(ByteBuf out) {

    }

    public static AbstractPacket decode(ByteBuf frame) {
        return new VersionCheckPacket();
    }

}
