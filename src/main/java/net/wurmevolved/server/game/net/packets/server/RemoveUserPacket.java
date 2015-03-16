package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_REMOVE_USER)
public class RemoveUserPacket extends AbstractPacket {

    private String channel;
    private String username;

    public RemoveUserPacket(String channel, String username) {
        this.channel = channel;
        this.username = username;
    }

    @Override
    public void encode(ByteBuf out) {
        writeString(out, getChannel());
        writeString(out, getUsername());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        String channel = readString(frame);
        String username = readString(frame);
        return new RemoveUserPacket(channel, username);
    }

    public String getChannel() {
        return channel;
    }

    public String getUsername() {
        return username;
    }

}
