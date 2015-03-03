package com.wurmemu.server.game.net.packets

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.client.ClientMessagePacket
import com.wurmemu.server.game.net.packets.client.HostHashPacket
import com.wurmemu.server.game.net.packets.client.LoginPacket
import com.wurmemu.server.game.net.packets.client.MovementPacket
import com.wurmemu.server.game.net.packets.client.NoMovementPacket
import com.wurmemu.server.game.net.packets.client.RenderStatsPacket
import com.wurmemu.server.game.net.packets.client.StackTracePacket
import com.wurmemu.server.game.net.packets.client.TeleportConfirmationPacket

class ClientPackets {

    static register() {
        PacketRegistry.instance.register(Protocol.PACKET_LOGIN, LoginPacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_MESSAGE, ClientMessagePacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_MOVEMENT, MovementPacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_NO_MOVEMENT, NoMovementPacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_TELEPORT, TeleportConfirmationPacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_HOST_HASH, HostHashPacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_STACK_TRACE, StackTracePacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_RENDER_STATS, RenderStatsPacket.class)
    }

}
