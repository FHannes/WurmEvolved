package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_OPENED_CRAFTING_WINDOW)
class OpenedCraftingWindowPacket extends AbstractPacket {

    @Override
    encode(ByteBuf out) {

    }

    static decode(ByteBuf frame) {
        new OpenedCraftingWindowPacket()
    }

}
