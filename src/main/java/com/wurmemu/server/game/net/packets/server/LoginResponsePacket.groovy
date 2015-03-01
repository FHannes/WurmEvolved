package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class LoginResponsePacket extends Packet {

    boolean allowLogin
    String reason
    byte layer
    boolean developer

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_LOGIN_RESPONSE)
        out.writeBoolean(allowLogin)
        writeString(out, reason)
        out.writeByte(layer)
        out.writeLong((long) System.currentTimeMillis() / 8)
        out.writeLong((long) System.currentTimeMillis() / 8)
        out.writeFloat(0)
        out.writeFloat(0)
        out.writeFloat(0)
        out.writeFloat(0)
        writeString(out, "model.creature.humanoid.human.player.male.free")
        out.writeBoolean(developer)
        out.writeBoolean(false)
        out.writeShort(0)
        out.writeLong(0)
        out.writeByte(0)
        out.writeInt(0)
        out.writeByte(0)
    }

}
