package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.constants.Material;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_ADD_OBJECT)
public class AddObjectPacket extends AbstractPacket {

    private long id;
    private String model;
    private Position pos;
    private String name;
    private Material material;

    public AddObjectPacket(long id, String model, Position pos, String name, Material material) {
        this.id = id;
        this.model = model;
        this.pos = pos;
        this.name = name;
        this.material = material;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(0);
        writeString(out, getModel());
        writeString(out, getName());
        out.writeLong(getId());
        out.writeByte(getMaterial().getId());
        out.writeFloat(getPos().getObjectX());
        out.writeFloat(getPos().getObjectY());
        out.writeFloat(getPos().getZ());
        out.writeFloat(getPos().getRot());
        out.writeByte(getPos().getLayer().getId());
        out.writeFloat(100);
        out.writeFloat(100);
        out.writeFloat(100);
        out.writeLong(0);
        out.writeLong(0);
        out.writeFloat(0);
        out.writeFloat(0);
    }

    public long getId() {
        return id;
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

    public Material getMaterial() {
        return material;
    }

}
