package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.common.MessagePacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

import java.awt.*;

@Packet(Protocol.PACKET_MESSAGE)
public class ServerMessagePacket extends MessagePacket {

    private Color color;

    public ServerMessagePacket(String channel, String message, Color color) {
        super(channel, message);
        this.color = color;
    }

    @Override
    public void encode(ByteBuf out) {
        writeString(out, getChannel());
        out.writeByte(getColor().getRed());
        out.writeByte(getColor().getGreen());
        out.writeByte(getColor().getBlue());
        writeLongString(out, getMessage());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        String channel = readString(frame);
        int r = frame.readByte() & 0xFF;
        int g = frame.readByte() & 0xFF;
        int b = frame.readByte() & 0xFF;
        String message = readLongString(frame);
        return new ServerMessagePacket(channel, message, new Color(r, g, b));
    }

    public Color getColor() {
        return color;
    }

}
