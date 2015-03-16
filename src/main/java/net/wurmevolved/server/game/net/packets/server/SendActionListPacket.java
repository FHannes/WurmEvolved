package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.menu.MenuItem;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

import java.util.Collection;

@Packet(Protocol.PACKET_SEND_ACTIONLIST)
public class SendActionListPacket extends AbstractPacket {

    private byte requestID;
    private Collection<MenuItem> actions;
    private String wiki;

    public SendActionListPacket(byte requestID, Collection<MenuItem> actions, String wiki) {
        this.requestID = requestID;
        this.actions = actions;
        this.wiki = wiki;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeByte(getRequestID());
        out.writeByte(getActions().size());
        for (MenuItem action : getActions()) {
            out.writeShort(action.getId());
            writeString(out, action.getCaption());
            out.writeBoolean(false); // Seemingly unused boolean value [3.54]
        }
        writeString(out, getWiki());
    }

    public byte getRequestID() {
        return requestID;
    }

    public Collection<MenuItem> getActions() {
        return actions;
    }

    public String getWiki() {
        return wiki;
    }

}
