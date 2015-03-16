package net.wurmevolved.server.game.map;

import net.wurmevolved.common.constants.TileType;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.data.TilePos;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.TileDAO;

import java.util.logging.Logger;

public class Chunk {

    private static final Logger LOGGER = Logger.getLogger(Chunk.class.getName());

    public static final short CHUNK_SIZE = 32;

    private short xPos;
    private short yPos;

    private short xOffset;
    private short yOffset;

    private Tile[][] tiles;

    public static short mapToChunk(short coord) {
        return (short) (coord / CHUNK_SIZE);
    }

    public Chunk(short xPos, short yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xOffset = (short) (xPos * CHUNK_SIZE);
        this.yOffset = (short) (yPos * CHUNK_SIZE);
        tiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];
    }

    public void save() {
        TileDAO dao = (TileDAO) DB.getInstance().getDAO("tileDAO");

        dao.save(tiles);
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            return tiles[y][x];
        } else {
            LOGGER.severe("Position ${x}, ${y} out of bounds");
            return null;
        }
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            tiles[y][x] = tile;
        } else {
            LOGGER.severe("Position ${x}, ${y} out of bounds");
        }
    }

    public void fill() {
        for (short y = 0; y < CHUNK_SIZE; y++) {
            for (short x = 0; x < CHUNK_SIZE; x++) {
                if (tiles[y][x] == null) {
                    tiles[y][x] = new Tile(new TilePos((short) (xOffset + x), (short) (yOffset + y)),
                            TileType.DIRT, (byte) 0, (byte) 20, (short) 0);
                }
            }
        }
    }

    public Tile[][] toArray() {
        Tile[][] tiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];

        for (short y = 0; y < CHUNK_SIZE; y++) {
            System.arraycopy(this.tiles[y], 0, tiles[y], 0, CHUNK_SIZE);
        }

        return tiles;
    }

}
