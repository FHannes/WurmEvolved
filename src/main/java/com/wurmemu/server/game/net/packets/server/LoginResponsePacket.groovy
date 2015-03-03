package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_LOGIN_RESPONSE)
class LoginResponsePacket extends AbstractPacket {

    boolean allowLogin
    String reason
    byte layer
    float x
    float y
    float z
    boolean developer

    @Override
    void encode(ByteBuf out) {
        out.writeBoolean(allowLogin)
        writeLongString(out, reason)
        out.writeByte(layer)
        out.writeLong((long) System.currentTimeMillis() * 8)
        out.writeLong((long) System.currentTimeMillis() * 8)
        out.writeFloat(0)
        out.writeFloat(x)
        out.writeFloat(y)
        out.writeFloat(z)
        writeLongString(out, "model.creature.humanoid.human.player.male.free")
        out.writeBoolean(developer)
        out.writeBoolean(false)
        out.writeShort(0)
        out.writeLong(0)
        out.writeByte(0)
        out.writeInt(0)
        out.writeByte(0)
    }

}
