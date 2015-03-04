package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.constants.ClientFeature
import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import com.wurmemu.server.game.net.packets.client.ToggleButtonPacket
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_TOGGLE_FEATURE)
class ToggleFeaturePacket extends AbstractPacket {

    ClientFeature feature
    byte value

    @Override
    encode(ByteBuf out) {
        out.writeByte(feature.ordinal())
        out.writeByte(value)
    }

    static decode(ByteBuf frame) {
        def feature = ClientFeature.values()[frame.readByte()] ?: ClientFeature.UNKNOWN
        def value = frame.readByte()
        new ToggleButtonPacket(feature: feature, value: value)
    }

}
