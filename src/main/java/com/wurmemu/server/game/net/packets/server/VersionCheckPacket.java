package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_VERSION_CHECK)
public class VersionCheckPacket extends AbstractPacket {

    @Override
    public void encode(ByteBuf out) {

    }

    public static AbstractPacket decode(ByteBuf frame) {
        return new VersionCheckPacket();
    }

}
