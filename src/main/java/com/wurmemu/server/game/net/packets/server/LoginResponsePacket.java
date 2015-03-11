package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.constants.Kingdom;
import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.data.FaceStyle;
import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_LOGIN_RESPONSE)
public class LoginResponsePacket extends AbstractPacket {

    boolean allowLogin;
    String reason;
    Position pos;
    String model;
    boolean developer;
    FaceStyle faceStyle;
    Kingdom kingdom;

    public LoginResponsePacket(boolean allowLogin, String reason, Position pos, String model, boolean developer, FaceStyle faceStyle, Kingdom kingdom) {
        this.allowLogin = allowLogin;
        this.reason = reason;
        this.pos = pos;
        this.model = model;
        this.developer = developer;
        this.faceStyle = faceStyle;
        this.kingdom = kingdom;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeBoolean(isAllowLogin());
        writeLongString(out, getReason());
        out.writeByte(getPos().getLayer());
        out.writeLong(System.currentTimeMillis() * 8);
        out.writeLong(System.currentTimeMillis() * 8);
        out.writeFloat(getPos().getRot());
        out.writeFloat(getPos().getObjectX());
        out.writeFloat(getPos().getObjectY());
        out.writeFloat(getPos().getZ());
        writeLongString(out, getModel());
        out.writeBoolean(isDeveloper());
        out.writeBoolean(false);
        out.writeShort(0);
        out.writeLong(getFaceStyle().toLong());
        out.writeByte(getKingdom().ordinal());
        out.writeInt(0); // Teleport ID
        out.writeByte(0); // Hair type (no use as of yet [3.83], leave 0)
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

    public String getModel() {
        return model;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public FaceStyle getFaceStyle() {
        return faceStyle;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

}
