package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_FIGHTING_STYLE)
public class FightingStylePacket extends AbstractPacket {

    private byte style;

    public FightingStylePacket(byte style) {
        this.style = style;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(getStyle());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        byte style = frame.readByte();
        return new FightingStylePacket(style);
    }

    public byte getStyle() {
        return style;
    }

}
