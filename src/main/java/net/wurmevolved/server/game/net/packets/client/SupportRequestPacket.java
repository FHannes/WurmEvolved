package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_SUPPORT)
public class SupportRequestPacket extends AbstractPacket {

    private byte category; // TODO: Replace with enum
    private String issue;

    public SupportRequestPacket(byte category, String issue) {
        this.category = category;
        this.issue = issue;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(getCategory());
        writeString(out, getIssue());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        byte category = frame.readByte();
        String issue = readString(frame);
        return new SupportRequestPacket(category, issue);
    }

    public byte getCategory() {
        return category;
    }

    public String getIssue() {
        return issue;
    }

}
