package com.wurmemu.server.game.net.packets.client

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_TOGGLE_BUTTON)
class ToggleButtonPacket extends AbstractPacket {

    byte buttonID
    boolean toggleOn

    @Override
    encode(ByteBuf out) {
        out.writeByte(buttonID)
        out.writeBoolean(toggleOn)
    }

    static decode(ByteBuf frame) {
        def buttonID = frame.readByte()
        def toggledOn = frame.readBoolean()
        new ToggleButtonPacket(buttonID: buttonID, toggleOn: toggledOn)
    }

}
