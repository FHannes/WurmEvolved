package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_LOGIN)
public class LoginPacket extends AbstractPacket {

    int protocol;
    String username;
    boolean developer;

    public LoginPacket(int protocol, String username, boolean developer) {
        this.protocol = protocol;
        this.username = username;
        this.developer = developer;
        this.developer = true; // TODO: Remove after debugging
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeInt(getProtocol());
        writeString(out, getUsername());
        writeString(out, String.format("%s,%s", getUsername(), isDeveloper() ? "true" : "false"));
    }

    public static AbstractPacket decode(ByteBuf frame) {
        int protocol = frame.readInt();
        String username = readString(frame);
        String[] data = readString(frame).split(",");
        return new LoginPacket(protocol, username, Boolean.parseBoolean(data[1]));
    }

    public int getProtocol() {
        return protocol;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDeveloper() {
        return developer;
    }

}
