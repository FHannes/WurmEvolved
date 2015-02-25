package com.wurmemu.server.game.net.packets

import com.wurmemu.server.game.net.Packet

class LoginPacket extends Packet {

    int protocol
    String username
    boolean developer

}
