package com.wurmemu.server.game.map

import com.wurmemu.server.game.data.Position
import com.wurmemu.server.game.data.Tile
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.TileDAO

class TerrainBuffer {

    static CHUNK_COUNT = 32

    Chunk[][] chunks

    TerrainBuffer() {
        chunks = new Chunk[CHUNK_COUNT][CHUNK_COUNT]
        (0..CHUNK_COUNT - 1). each { y ->
            (0..CHUNK_COUNT - 1). each { x ->
                chunks[y][x] = new Chunk(x, y)
            }
        }
    }

    Tile getTile(int x, int y) {
        getChunk(Chunk.mapToChunk(x), Chunk.mapToChunk(y)).getTile(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE)
    }

    void getTile(Position position) {
        getTile(position.tileX, position.tileY)
    }

    void setTile(int x, int y, Tile tile) {
        getChunk(Chunk.mapToChunk(x), Chunk.mapToChunk(y)).setTile(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE, tile)
    }

    Chunk getChunk(int x, int y) {
        chunks[y][x]
    }

    void load() {
        TileDAO dao = DB.instance.getDAO("tileDAO")
        dao.list().each { tile -> setTile(tile.pos.x, tile.pos.y, tile) }
        (0..CHUNK_COUNT - 1). each { y ->
            (0..CHUNK_COUNT - 1). each { x ->
                chunks[y][x].fill()
            }
        }
    }

    void save() {
        (0..CHUNK_COUNT - 1). each { y ->
            (0..CHUNK_COUNT - 1). each { x ->
                chunks[y][x].save()
            }
        }
    }

    List<Chunk> getChunks(int x, int y, int chunkRange) {
        def chunks = new ArrayList()
        int x1 = x - chunkRange
        x1 = x1 < 0 ? 0 : x1
        int y1 = y - chunkRange
        y1 = y1 < 0 ? 0 : y1
        int x2 = x + chunkRange
        x2 = x2 >= CHUNK_COUNT ? CHUNK_COUNT - 1 : x2
        int y2 = y + chunkRange
        y2 = y2 >= CHUNK_COUNT ? CHUNK_COUNT - 1 : y2
        (y1..y2).each { yy -> (x1..x2).each { xx -> chunks.add(getChunk(xx, yy)) } }
        chunks
    }

    List<Chunk> getChunksFromCoords(int x, int y, int chunkRange) {
        getChunks(Chunk.mapToChunk(x), Chunk.mapToChunk(y), chunkRange)
    }

    void updatePosition(Position pos) {
        def relX = pos.tileRelativeX
        def relY = pos.tileRelativeY

        Tile tile = getTile(pos)
        Tile tileR = getTile(tile.pos.x + 1, tile.pos.y)
        Tile tileD = getTile(tile.pos.x, tile.pos.y + 1)
        Tile tileRD = getTile(tile.pos.x + 1, tile.pos.y + 1)

        def top = relX * tile.height + (1 - relX) * tileR.height
        def bottom = relX * tileD.height + (1 - relX) * tileRD.height

        pos.z = (relY * top + (1 - relY) * bottom) / 10
    }

}
