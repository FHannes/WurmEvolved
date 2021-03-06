package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

@Packet(Protocol.PACKET_DISTANT_TERRAIN)
public class DistantTerrainPacket extends AbstractPacket {

    private Tile[][] tiles;

    public DistantTerrainPacket(Tile[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeShort(getTiles()[0][0].getPos().getX() / 16);
        out.writeShort(getTiles()[0][0].getPos().getY() / 16);
        int sizeX = getTiles()[0].length;
        int sizeY = getTiles().length;
        out.writeShort(sizeX);
        out.writeShort(sizeY);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                out.writeShort(getTiles()[y][x].getHeight());
            }
        }
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                out.writeByte(getTiles()[y][x].getType().getType());
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

}
