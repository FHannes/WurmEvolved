package com.wurmemu.server.game.net.packets.common;

import com.wurmemu.server.game.net.packets.AbstractPacket;

public abstract class MessagePacket extends AbstractPacket {

    private String channel;
    private String message;

    public MessagePacket(String channel, String message) {
        this.channel = channel;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

}
