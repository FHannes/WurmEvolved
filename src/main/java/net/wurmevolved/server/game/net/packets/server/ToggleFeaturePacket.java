package net.wurmevolved.server.game.net.packets.server;

import net.wurmevolved.common.constants.ClientFeature;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_TOGGLE_FEATURE)
public class ToggleFeaturePacket extends AbstractPacket {

    private ClientFeature feature;
    private byte value;

    public ToggleFeaturePacket(ClientFeature feature, byte value) {
        this.feature = feature;
        this.value = value;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(getFeature().ordinal());
        out.writeByte(getValue());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        ClientFeature feature = ClientFeature.values()[frame.readByte()];
        if (feature == null) {
            feature = ClientFeature.UNKNOWN;
        }
        byte value = frame.readByte();
        return new ToggleFeaturePacket(feature, value);
    }

    public ClientFeature getFeature() {
        return feature;
    }

    public byte getValue() {
        return value;
    }

}
