package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_TELEPORT)
public class TeleportConfirmationPacket extends AbstractPacket {

    private int teleportID;

    public TeleportConfirmationPacket(int teleportID) {
        this.teleportID = teleportID;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeInt(getTeleportID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        int teleportID = frame.readInt();
        return new TeleportConfirmationPacket(teleportID);
    }

    public int getTeleportID() {
        return teleportID;
    }

}
