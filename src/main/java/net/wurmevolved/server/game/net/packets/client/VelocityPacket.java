package net.wurmevolved.server.game.net.packets.client;

import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_VELOCITY)
public class VelocityPacket extends AbstractPacket {

    private float velocity;

    public VelocityPacket(float velocity) {
        this.velocity = velocity;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeFloat(getVelocity());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        float velocity = frame.readFloat();
        return new VelocityPacket(velocity);
    }

    public float getVelocity() {
        return velocity;
    }

}
