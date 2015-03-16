package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_MOVEMENT)
public class MovementPacket extends AbstractPacket {

    private Position pos;
    private byte detection;

    public MovementPacket(Position pos, byte detection) {
        this.pos = pos;
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
        pos.setRot(frame.readFloat());
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
        return new MovementPacket(pos, detection);
    }

    public Position getPos() {
        return pos;
    }

    public byte getDetection() {
        return detection;
    }

}
