package com.wurmemu.server.game.net.packets

import com.wurmemu.server.game.net.Packet

abstract class MessagePacket extends Packet {

    String channel
    String message

}
