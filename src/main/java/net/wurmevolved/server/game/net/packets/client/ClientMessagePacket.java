package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import net.wurmevolved.server.game.net.packets.common.MessagePacket;

@Packet(Protocol.PACKET_MESSAGE)
public class ClientMessagePacket extends MessagePacket {

    public ClientMessagePacket(String channel, String message) {
        super(channel, message);
    }

    @Override
    public void encode(ByteBuf out) {
        writeString(out, getMessage());
        writeString(out, getChannel());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        String message = readString(frame);
        String channel = readString(frame);
        return new ClientMessagePacket(channel, message);
    }

}
