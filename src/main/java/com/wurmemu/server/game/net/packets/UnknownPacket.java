package com.wurmemu.server.game.net.packets;

import io.netty.buffer.ByteBuf;

public class UnknownPacket extends AbstractPacket {

    private int type;
    private ByteBuf frame;

    public UnknownPacket(int type, ByteBuf frame) {
        this.type = type;
        this.frame = frame;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(type);
        out.writeBytes(frame);
    }

    public int getType() {
        return type;
    }

    public ByteBuf getFrame() {
        return frame;
    }

}
