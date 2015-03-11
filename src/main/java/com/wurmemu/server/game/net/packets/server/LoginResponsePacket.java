package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_LOGIN_RESPONSE)
public class LoginResponsePacket extends AbstractPacket {

    boolean allowLogin;
    String reason;
    Position pos;
    boolean developer;

    public LoginResponsePacket(boolean allowLogin, String reason, Position pos, boolean developer) {
        this.allowLogin = allowLogin;
        this.reason = reason;
        this.pos = pos;
        this.developer = developer;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeBoolean(isAllowLogin());
        writeLongString(out, getReason());
        out.writeByte(getPos().getLayer());
        out.writeLong(System.currentTimeMillis() * 8);
        out.writeLong(System.currentTimeMillis() * 8);
        out.writeFloat(0);
        out.writeFloat(getPos().getClientX());
        out.writeFloat(getPos().getClientY());
        out.writeFloat(getPos().getZ());
        writeLongString(out, "model.creature.humanoid.human.player.male.free");
        out.writeBoolean(isDeveloper());
        out.writeBoolean(false);
        out.writeShort(0);
        out.writeLong(0);
        out.writeByte(0);
        out.writeInt(0);
        out.writeByte(0);
    }

    public boolean isAllowLogin() {
        return allowLogin;
    }

    public String getReason() {
        return reason;
    }

    public Position getPos() {
        return pos;
    }

    public boolean isDeveloper() {
        return developer;
    }

}
