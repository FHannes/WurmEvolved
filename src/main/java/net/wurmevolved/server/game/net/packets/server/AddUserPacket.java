package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_ADD_USER)
public class AddUserPacket extends AbstractPacket {

    private String channel;
    private String username;
    private long userID;

    public AddUserPacket(String channel, String username, long userID) {
        this.channel = channel;
        this.username = username;
        this.userID = userID;
    }

    @Override
    public void encode(ByteBuf out) {
        writeString(out, getChannel());
        writeString(out, getUsername());
        out.writeLong(getUserID());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        String channel = readString(frame);
        String username = readString(frame);
        long userID = frame.readLong();
        return new AddUserPacket(channel, username, userID);
    }

    public String getChannel() {
        return channel;
    }

    public String getUsername() {
        return username;
    }

    public long getUserID() {
        return userID;
    }

}
