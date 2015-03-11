package com.wurmemu.server.game.net.packets.server;

import com.wurmemu.common.protocol.Protocol;
import com.wurmemu.server.game.data.Tile;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import com.wurmemu.server.game.net.packets.Packet;
import io.netty.buffer.ByteBuf;

@Packet(Protocol.PACKET_TERRAIN)
public class TerrainPacket extends AbstractPacket {

    private Tile[][] tiles;

    public TerrainPacket(Tile[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeShort(getTiles()[0][0].getPos().getX());
        out.writeShort(getTiles()[0][0].getPos().getY());
        int sizeX = getTiles()[0].length;
        int sizeY = getTiles().length;
        out.writeShort(sizeX);
        out.writeShort(sizeY);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Tile tile = getTiles()[y][x];
                int tileData = tile.getHeight() & 0xFFFF;
                tileData += tile.getType().getType() << 24;
                tileData += tile.getSubtype() << 16;
                tileData += tile.getAge() << 20;
                out.writeInt(tileData);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

}
