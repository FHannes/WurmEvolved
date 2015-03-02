package com.wurmemu.server.game.map

import com.wurmemu.server.game.data.Tile

class TerrainBuffer {

    static CHUNK_COUNT = 32

    Chunk[][] chunks

    TerrainBuffer() {
        chunks = new Chunk[CHUNK_COUNT][CHUNK_COUNT]
    }

    Tile getTile(int x, int y) {
        getChunk(Chunk.mapToChunk(x), Chunk.mapToChunk(y)).getTile(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE)
    }

    Chunk getChunk(int x, int y) {
        if (chunks[y][x] == null) {
            chunks[y][x] = new Chunk(x, y)
        }
        chunks[y][x]
    }

    void save() {
        (1..CHUNK_COUNT - 1). each {
            y -> (1..CHUNK_COUNT - 1). each {
                x -> chunks[y][x].save()
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

}
