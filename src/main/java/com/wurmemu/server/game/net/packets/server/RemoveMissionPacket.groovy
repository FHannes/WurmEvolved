package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.MessagePacket
import com.wurmemu.server.game.net.packets.Packet
import io.netty.buffer.ByteBuf

@Packet(Protocol.PACKET_REMOVE_MISSION)
class RemoveMissionPacket extends MessagePacket {

    long missionID

    @Override
    encode(ByteBuf out) {
        out.writeLong(missionID)
    }

    static decode(ByteBuf frame) {
        def missionID = frame.readLong()
        new RemoveMissionPacket(missionID: missionID)
    }

}
