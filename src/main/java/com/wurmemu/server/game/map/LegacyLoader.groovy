package com.wurmemu.server.game.map

import com.wurmemu.common.constants.TileType
import com.wurmemu.server.game.data.Tile

class LegacyLoader {

    File mapFile
    int size

    void load(TerrainBuffer terrainBuffer) {
        def dis = new DataInputStream(new BufferedInputStream(new FileInputStream(mapFile)))
        try {
            for (short y = 0; y < size; y++)
                for (short x = 0; x < size; x++) {
                    if (dis.available() == 0) {
                        y = size
                        break
                    }
                    if (dis.readByte() != 1) {
                        dis.skipBytes(5)
                        continue
                    }
                    def tile = terrainBuffer.getTile(x, y)
                    tile.height = dis.readShort()
                    def type = dis.readByte()
                    tile.type = TileType.get(type)
                    tile.subtype = dis.readByte()
                    tile.age = dis.readByte()
                    mapTile(tile, type)
                }
        } finally {
            dis.close()
        }
        terrainBuffer.save()
    }

    static void mapTile(Tile tile, int type) {
        type = (type + 256) % 256
        switch (type) {
            case 0:
                tile.type = TileType.ROCK
                break;
            case 25..29:
                tile.type = TileType.ROCK
                break;
            case 100..113:
                tile.subtype = type - 100
                tile.type = TileType.TREE
                break
            case 114..127:
                tile.subtype = type - 114
                tile.type = TileType.INFECTED_TREE
                break
            case 128..141:
                tile.subtype = type - 128
                tile.type = TileType.ENCHANTED_TREE
                break
            case 142..147:
                tile.subtype = type - 142
                tile.type = TileType.BUSH
                break
            case 148..153:
                tile.subtype = type - 148
                tile.type = TileType.INFECTED_BUSH
                break
            case 154..159:
                tile.subtype = type - 154
                tile.type = TileType.ENCHANTED_BUSH
                break
        }
    }

}
