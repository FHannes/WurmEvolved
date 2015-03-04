package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.data.Position
import com.wurmemu.server.game.net.packets.AbstractPacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_LOGIN_RESPONSE)
class LoginResponsePacket extends AbstractPacket {

    boolean allowLogin
    String reason
    Position pos
    boolean developer

    @Override
    encode(ByteBuf out) {
        out.writeBoolean(allowLogin)
        writeLongString(out, reason)
        out.writeByte(pos.layer)
        out.writeLong((long) System.currentTimeMillis() * 8)
        out.writeLong((long) System.currentTimeMillis() * 8)
        out.writeFloat(0)
        out.writeFloat(pos.clientX)
        out.writeFloat(pos.clientY)
        out.writeFloat(pos.z)
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
