package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.constants.CreatureType;
import net.wurmevolved.common.constants.Kingdom;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.data.FaceStyle;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_ADD_CREATURE)
public class AddCreaturePacket extends AbstractPacket {

    private long playerID;
    private String model;
    private Position pos;
    private String name;
    private CreatureType type;
    private Kingdom kingdom;
    private FaceStyle faceStyle;

    public AddCreaturePacket(long playerID, String model, Position pos, String name, CreatureType type, Kingdom kingdom, FaceStyle faceStyle) {
        this.playerID = playerID;
        this.model = model;
        this.pos = pos;
        this.name = name;
        this.type = type;
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
        out.writeFloat(getPos().getRot());
        writeString(out, name);
        out.writeBoolean(true); // Never submerge
        out.writeByte(getPos().getLayer());
        out.writeByte(getType().ordinal());
        out.writeByte(0); // Material
        out.writeByte(getKingdom().ordinal());
        out.writeLong(getFaceStyle().toLong());
        if (getType().equals(CreatureType.HUMAN)) {
            out.writeInt(0); // Unknown value (seems to be unused [3.83])
        }
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
        CreatureType type = CreatureType.values()[frame.readByte() & 0xFF];
        frame.readByte();
        Kingdom kingdom = Kingdom.values()[frame.readByte() & 0xFF];
        FaceStyle faceStyle = new FaceStyle();
        faceStyle.fromLong(frame.readLong());
        if (type.equals(CreatureType.HUMAN)) {
            frame.readInt();
        }
        frame.readByte();
        if (frame.isReadable()) {
            frame.readByte();
        }
        return new AddCreaturePacket(playerID, model, pos, name, type, kingdom, faceStyle);
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

    public CreatureType getType() {
        return type;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public FaceStyle getFaceStyle() {
        return faceStyle;
    }

}
