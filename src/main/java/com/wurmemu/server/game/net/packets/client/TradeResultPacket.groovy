package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_TRADE_RESULT)
class TradeResultPacket extends AbstractPacket {

    boolean agree

    @Override
    void encode(ByteBuf out) {
        out.writeBoolean(agree)
    }

    static TradeResultPacket decode(ByteBuf frame) {
        def agree = frame.readBoolean()
        new TradeResultPacket(agree: agree)
    }

}
