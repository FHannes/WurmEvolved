package com.wurmemu.server.game.map;

import com.wurmemu.server.game.data.Position;
import com.wurmemu.server.game.data.Tile;
import com.wurmemu.server.game.data.db.DB;
import com.wurmemu.server.game.data.db.dao.TileDAO;

import java.util.ArrayList;
import java.util.List;

public class TerrainBuffer {

    public static final short CHUNK_COUNT = 32;

    private Chunk[][] chunks;

    public TerrainBuffer() {
        chunks = new Chunk[CHUNK_COUNT][CHUNK_COUNT];
        for (short y = 0; y < CHUNK_COUNT; y++) {
            for (short x = 0; x < CHUNK_COUNT; x++) {
                chunks[y][x] = new Chunk(x, y);
            }
        }
    }

    public Tile getTile(short x, short y) {
        return getChunk(Chunk.mapToChunk(x), Chunk.mapToChunk(y)).getTile(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE);
    }

    public Tile getTile(Position position) {
        return getTile(position.getTileX(), position.getTileY());
    }

    public void setTile(short x, short y, Tile tile) {
        getChunk(Chunk.mapToChunk(x), Chunk.mapToChunk(y)).setTile(x % Chunk.CHUNK_SIZE, y % Chunk.CHUNK_SIZE, tile);
    }

    public Chunk getChunk(short x, short y) {
        return chunks[y][x];
    }

    public void load() {
        TileDAO dao = (TileDAO) DB.getInstance().getDAO("tileDAO");
        for (Tile tile : dao.list()) {
            setTile(tile.getPos().getX(), tile.getPos().getY(), tile);
        }
        for (int y = 0; y < CHUNK_COUNT; y++) {
            for (int x = 0; x < CHUNK_COUNT; x++) {
                chunks[y][x].fill();
            }
        }
    }

    public void save() {
        for (int y = 0; y < CHUNK_COUNT; y++) {
            for (int x = 0; x < CHUNK_COUNT; x++) {
                chunks[y][x].save();
            }
        }
    }

    public List<Chunk> getChunks(short x, short y, short chunkRange) {
        List<Chunk> chunks = new ArrayList<>();
        short x1 = (short) (x - chunkRange);
        x1 = x1 < 0 ? 0 : x1;
        short y1 = (short) (y - chunkRange);
        y1 = y1 < 0 ? 0 : y1;
        short x2 = (short) (x + chunkRange);
        x2 = x2 >= CHUNK_COUNT ? CHUNK_COUNT - 1 : x2;
        short y2 = (short) (y + chunkRange);
        y2 = y2 >= CHUNK_COUNT ? CHUNK_COUNT - 1 : y2;
        for (short yy = y1; yy <= y2; yy++) {
            for (short xx = x1; xx <= x2; xx++) {
                chunks.add(getChunk(xx, yy));
            }
        }
        return chunks;
    }

    public List<Chunk> getChunksFromCoords(short x, short y, short chunkRange) {
        return getChunks(Chunk.mapToChunk(x), Chunk.mapToChunk(y), chunkRange);
    }

    public void updatePosition(Position pos) {
        short relX = pos.getTileRelativeX();
        short relY = pos.getTileRelativeX();

        Tile tile = getTile(pos);
        Tile tileR = getTile((short) (tile.getPos().getX() + 1), tile.getPos().getY());
        Tile tileD = getTile(tile.getPos().getX(), (short) (tile.getPos().getY() + 1));
        Tile tileRD = getTile((short) (tile.getPos().getX() + 1), (short) (tile.getPos().getY() + 1));

        int top = relX * tile.getHeight() + (1 - relX) * tileR.getHeight();
        int bottom = relX * tileD.getHeight() + (1 - relX) * tileRD.getHeight();

        pos.setZ((relY * top + (1 - relY) * bottom) / 10);
    }

}
