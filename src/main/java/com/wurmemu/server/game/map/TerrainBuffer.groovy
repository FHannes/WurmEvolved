package com.wurmemu.server.game.map

import com.wurmemu.server.game.data.Tile

class TerrainBuffer {

    static CHUNK_COUNT = 16

    Chunk[][] chunks

    TerrainBuffer() {
        chunks = new Chunk[CHUNK_COUNT][CHUNK_COUNT]
    }

    Tile getTile(int x, int y) {
        int chunkX = x / Chunk.CHUNK_SIZE
        int chunkY = y / Chunk.CHUNK_SIZE
        if (chunks[chunkY][chunkX] == null) {
            chunks[chunkY][chunkX] = new Chunk(chunkX, chunkY)
        }
        chunks[chunkY][chunkX].getTile(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE)
    }

}
