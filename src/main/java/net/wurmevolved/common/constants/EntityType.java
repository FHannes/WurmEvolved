package net.wurmevolved.common.constants;

public class EntityType {

    public static final int ITEM = 2;
    public static final int TILE = 3;
    public static final int PLAYER = 6;
    public static final int TILE_BORDER = 7;

    public static boolean isIDOfType(long id, int type) {
        return (id % 16) == type;
    }

}
