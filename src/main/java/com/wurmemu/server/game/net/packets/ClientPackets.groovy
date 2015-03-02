package com.wurmemu.server.game.net.packets

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.net.packets.client.ClientMessagePacket
import com.wurmemu.server.game.net.packets.client.LoginPacket

class ClientPackets {

    static register() {
        PacketRegistry.instance.register(Protocol.PACKET_LOGIN, LoginPacket.class)
        PacketRegistry.instance.register(Protocol.PACKET_MESSAGE, ClientMessagePacket.class)
    }

}
