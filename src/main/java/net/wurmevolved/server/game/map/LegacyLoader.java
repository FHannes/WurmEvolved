package net.wurmevolved.server.game.map;

import net.wurmevolved.common.constants.TileType;
import net.wurmevolved.server.game.data.Tile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class LegacyLoader {

    private File mapFile;
    private int size;

    public LegacyLoader(File mapFile, int size) {
        this.mapFile = mapFile;
        this.size = size;
    }

    public void load(TerrainBuffer terrainBuffer) {
        try (FileInputStream fis = new FileInputStream(mapFile)) {
            FileChannel channel = fis.getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(6 * size);
            for (short y = 0; y < size; y++) {
                int read;
                if ((read = channel.read(buf)) == -1) {
                    break;
                }
                buf.position(0);
                buf.limit(read);
                for (short x = 0; x < size; x++) {
                    if (buf.get() != 1) {
                        buf.position(buf.position() + 5);
                        continue;
                    }
                    Tile tile = terrainBuffer.getTile(x, y);
                    tile.setHeight(buf.getShort());
                    byte type = buf.get();
                    tile.setType(TileType.get(type));
                    tile.setSubtype(buf.get());
                    tile.setAge(buf.get());
                    mapTile(tile, type & 0xFF);
                }
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        terrainBuffer.save();
    }

    static void mapTile(Tile tile, int type) {
        if (type == 0 || type >= 25 && type <= 29) {
            tile.setType(TileType.ROCK);
        } else if (type >= 100 && type <= 113) {
            tile.setType(TileType.get((byte) (type - 100)));
            tile.setType(TileType.TREE);
        } else if (type >= 114 && type <= 127) {
            tile.setType(TileType.get((byte) (type - 114)));
            tile.setType(TileType.INFECTED_TREE);
        } else if (type >= 128 && type <= 141) {
            tile.setType(TileType.get((byte) (type - 128)));
            tile.setType(TileType.ENCHANTED_TREE);
        } else if (type >= 142 && type <= 147) {
            tile.setType(TileType.get((byte) (type - 142)));
            tile.setType(TileType.BUSH);
        } else if (type >= 148 && type <= 153) {
            tile.setType(TileType.get((byte) (type - 148)));
            tile.setType(TileType.INFECTED_BUSH);
        } else if (type >= 154 && type <= 159) {
            tile.setType(TileType.get((byte) (type - 154)));
            tile.setType(TileType.ENCHANTED_BUSH);
        }
    }

}
