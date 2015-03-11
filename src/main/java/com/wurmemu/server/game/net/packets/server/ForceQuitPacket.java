package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_FORCE_QUIT)
public class ForceQuitPacket extends AbstractPacket {

    private String reason;
    private boolean confirm;

    public ForceQuitPacket(String reason, boolean confirm) {
        this.reason = reason;
        this.confirm = confirm;
    }

    @Override
    public void encode(ByteBuf out) {
        if (getReason() != null && !getReason().equals("")) {
            writeLongString(out, getReason());
        }
        out.writeBoolean(isConfirm());
    }

    public String getReason() {
        return reason;
    }

    public boolean isConfirm() {
        return confirm;
    }

}
