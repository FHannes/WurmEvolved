package com.wurmemu.server.game.map

import com.wurmemu.common.constants.TileType

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
                    tile.type = TileType.get(dis.readByte())
                    tile.subtype = dis.readByte()
                    tile.age = dis.readByte()
                }
        } finally {
            dis.close()
        }
        terrainBuffer.save()
    }

}
