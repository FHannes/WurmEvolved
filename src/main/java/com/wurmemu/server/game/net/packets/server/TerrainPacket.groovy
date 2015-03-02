package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class TerrainPacket extends Packet {

    Tile[][] tiles

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_TERRAIN)
        out.writeShort(tiles[0][0].pos.x)
        out.writeShort(tiles[0][0].pos.y)
        def sizeX = tiles[0].length
        def sizeY = tiles.length
        out.writeShort(sizeX)
        out.writeShort(sizeY)
        (0..sizeX - 1).each { x ->
            (0..sizeY - 1).each { y ->
                Tile tile = tiles[y][x]
                int tileData = tile.height & 0xFFFF
                tileData += tile.type.type << 24
                tileData += tile.subtype << 16
                tileData += tile.age << 20
                out.writeInt(tileData)
            }
        }
    }

}
