package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_TRADE_RESULT)
public class TradeResultPacket extends AbstractPacket {

    private boolean agree;

    public TradeResultPacket(boolean agree) {
        this.agree = agree;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeBoolean(isAgree());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        boolean agree = frame.readBoolean();
        return new TradeResultPacket(agree);
    }

    public boolean isAgree() {
        return agree;
    }

}
