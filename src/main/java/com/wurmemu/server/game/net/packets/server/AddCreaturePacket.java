package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.constants.Kingdom;
import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.data.FaceStyle;
import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_ADD_CREATURE)
public class AddCreaturePacket extends AbstractPacket {

    private long playerID;
    private String model;
    private Position pos;
    private String name;
    private Kingdom kingdom;
    private FaceStyle faceStyle;

    public AddCreaturePacket(long playerID, String model, Position pos, String name, Kingdom kingdom, FaceStyle faceStyle) {
        this.playerID = playerID;
        this.model = model;
        this.pos = pos;
        this.name = name;
        this.kingdom = kingdom;
        this.faceStyle = faceStyle;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getPlayerID());
        out.writeByte(getModel().length());
        out.writeBoolean(true);
        out.writeFloat(getPos().getObjectY());
        out.writeFloat(getPos().getObjectX());
        out.writeBytes(getModel().getBytes());
        out.writeFloat(getPos().getZ());
        out.writeFloat(0); // Rotation
        writeString(out, name);
        out.writeBoolean(true); // Never submerge
        out.writeByte(getPos().getLayer());
        out.writeByte(0);
        out.writeByte(0); // Material
        out.writeByte(kingdom.ordinal()); // Kingdom
        out.writeLong(faceStyle.toLong());
        out.writeByte(0);
        out.writeByte(0); // Rarity (Optional)
    }

    public static AbstractPacket decode(ByteBuf frame) {
        Position pos = new Position();
        long playerID = frame.readLong();
        int modelLength = frame.readByte();
        frame.readBoolean();
        pos.setY(frame.readFloat() / 4);
        pos.setX(frame.readFloat() / 4);
        String model = readString(frame, modelLength);
        pos.setZ(frame.readFloat());
        frame.readFloat();
        String name = readString(frame);
        frame.readBoolean();
        pos.setLayer(frame.readByte());
        byte unknown = frame.readByte();
        frame.readByte();
        Kingdom kingdom = Kingdom.values()[frame.readByte() & 0xFF];
        FaceStyle faceStyle = new FaceStyle();
        faceStyle.fromLong(frame.readLong());
        if (unknown == 1) {
            frame.readInt();
        }
        frame.readByte();
        if (frame.isReadable()) {
            frame.readByte();
        }
        return new AddCreaturePacket(playerID, model, pos, name, kingdom, faceStyle);
    }

    public long getPlayerID() {
        return playerID;
    }

    public String getModel() {
        return model;
    }

    public Position getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public FaceStyle getFaceStyle() {
        return faceStyle;
    }

}
