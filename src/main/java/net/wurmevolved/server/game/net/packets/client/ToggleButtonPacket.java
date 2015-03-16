package net.wurmevolved.server.game.net.packets.client;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_TOGGLE_BUTTON)
public class ToggleButtonPacket extends AbstractPacket {

    private byte buttonID;
    private boolean toggleOn;

    public ToggleButtonPacket(byte buttonID, boolean toggleOn) {
        this.buttonID = buttonID;
        this.toggleOn = toggleOn;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(getButtonID());
        out.writeBoolean(isToggleOn());
    }

    public static AbstractPacket decode(ByteBuf frame) {
        byte buttonID = frame.readByte();
        boolean toggleOn = frame.readBoolean();
        return new ToggleButtonPacket(buttonID, toggleOn);
    }

    public byte getButtonID() {
        return buttonID;
    }

    public boolean isToggleOn() {
        return toggleOn;
    }

}
