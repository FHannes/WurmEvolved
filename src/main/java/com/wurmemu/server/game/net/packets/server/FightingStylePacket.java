package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

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
