package com.wurmemu.server.game.net.packets.client;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_MOVEMENT)
public class MovementPacket extends AbstractPacket {

    private float x;
    private float y;
    private float z;
    private float rotation;
    private byte detection;
    private byte layer;

    public MovementPacket(float x, float y, float z, float rotation, byte detection, byte layer) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
        this.detection = detection;
        this.layer = layer;
    }

    @Override
    public void encode(ByteBuf out) {
        //
    }

    public static AbstractPacket decode(ByteBuf frame) {
        float x = frame.readFloat();
        float y = frame.readFloat();
        float z = frame.readFloat();
        float rotation = frame.readFloat();
        byte detection = frame.readByte();
        byte layer = frame.readByte();
        for (int idx = 0; idx < 5; idx++) {
            // TODO: Figure out these values
            frame.readFloat();
            frame.readFloat();
            frame.readFloat();
            frame.readFloat();
            frame.readByte();
            frame.readByte();
        }
        return new MovementPacket(x, y, z, rotation, detection, layer);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getRotation() {
        return rotation;
    }

    public byte getDetection() {
        return detection;
    }

    public byte getLayer() {
        return layer;
    }

}
