package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_MOVEMENT)
public class MovementPacket extends AbstractPacket {

    private Position pos;
    private float rotation;
    private byte detection;

    public MovementPacket(Position pos, float rotation, byte detection) {
        this.pos = pos;
        this.rotation = rotation;
        this.detection = detection;
    }

    @Override
    public void encode(ByteBuf out) {
        //
    }

    public static AbstractPacket decode(ByteBuf frame) {
        Position pos = new Position();
        pos.setObjectX(frame.readFloat());
        pos.setObjectY(frame.readFloat());
        pos.setZ(frame.readFloat());
        float rotation = frame.readFloat();
        byte detection = frame.readByte();
        pos.setLayer(frame.readByte());
        for (int idx = 0; idx < 5; idx++) {
            // TODO: Figure out these values
            frame.readFloat();
            frame.readFloat();
            frame.readFloat();
            frame.readFloat();
            frame.readByte();
            frame.readByte();
        }
        return new MovementPacket(pos, rotation, detection);
    }

    public Position getPos() {
        return pos;
    }

    public float getRotation() {
        return rotation;
    }

    public byte getDetection() {
        return detection;
    }

}
