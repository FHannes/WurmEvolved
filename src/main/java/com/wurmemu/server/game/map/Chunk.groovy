package com.wurmemu.server.game.map

import com.wurmemu.common.constants.TileType
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.data.TilePos
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.TileDAO

import java.util.logging.Logger

class Chunk {

    static Logger LOGGER = Logger.getLogger(Chunk.class.getName());

    static CHUNK_SIZE = 64

    int xPos
    int yPos

    int xOffset
    int yOffset

    Tile[][] tiles

    Chunk(int xPos, int yPos) {
        this.xPos = xPos
        this.yPos = yPos
        this.xOffset = xPos * CHUNK_SIZE
        this.yOffset = yPos * CHUNK_SIZE
        tiles = new Tile[CHUNK_SIZE][CHUNK_SIZE]

        TileDAO dao = DB.instance.getDAO("tileDAO")
        dao.list(xOffset, yOffset, CHUNK_SIZE).each {
            tile -> tiles[tile.pos.y - yOffset][tile.pos.x - xOffset] = tile
        }

        (0..CHUNK_SIZE - 1).each {
            y -> (0..CHUNK_SIZE - 1).each {
                x -> if (tiles[y][x] == null) {
                    tiles[y][x] = new Tile(
                            pos: new TilePos(x: xOffset + x, y: yOffset + y),
                            type: TileType.DIRT, subtype: 0, height: 20, age: 0)
                }
            }
        }
    }

    void save() {
        TileDAO dao = DB.instance.getDAO("tileDAO")

        tiles.each { tileRow -> tileRow.each { tile -> dao.save(tile) } }
    }

    Tile getTile(int x, int y) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            tiles[y][x]
        } else {
            LOGGER.severe("Position ${x}, ${y} out of bounds")
            null
        }
    }

}
