package com.wurmemu.server.game.net.packets.server

import com.wurmemu.common.protocol.Protocol
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.net.Packet
import io.netty.buffer.ByteBuf

class DistantTerrainPacket extends Packet {

    Tile[][] tiles

    @Override
    void encode(ByteBuf out) {
        out.writeByte(Protocol.PACKET_DISTANT_TERRAIN)
        out.writeShort((short) tiles[0][0].pos.x / 16)
        out.writeShort((short) tiles[0][0].pos.y / 16)
        def sizeX = tiles[0].length
        def sizeY = tiles.length
        out.writeShort(sizeX)
        out.writeShort(sizeY)
        (0..sizeX - 1).each { x -> (0..sizeY - 1).each { y -> out.writeShort(tiles[y][x].height) } }
        (0..sizeX - 1).each { x -> (0..sizeY - 1).each { y -> out.writeByte(tiles[y][x].type.type) } }
    }

}
