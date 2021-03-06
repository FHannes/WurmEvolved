package net.wurmevolved.common.constants;

import java.awt.*;

public enum TileType {

    HOLE(0, "#000000"),
    SAND(1, "#A0936D"),
    GRASS(2, "#366503"),
    TREE(3, "#293A02"),
    ROCK(4, "#726E6B"),
    DIRT(5, "#4B3F2F"),
    CLAY(6, "#717C76"),
    FIELD(7, "#473C2F"),
    PACKED_DIRT(8, "#4B3F2F"),
    COBBLESTONE(9, "#5C5349"),
    MYCELIUM(10, "#470233"),
    INFECTED_TREE(11, "#3A0229"),
    LAVA(12, "#D7331E"),
    ENCHANTED_GRASS(13, "#2D5D2B"),
    ENCHANTED_TREE(14, "#1A3418"),
    WOODEN_PLANKS(15, "#726650"),
    STONE_SLABS(16, "#636363"),
    GRAVEL(17, "#4F4A40"),
    PEAT(18, "#362720"),
    TUNDRA(19, "#76876D"),
    MOSS(20, "#6A8E38"),
    CLIFF(21, "#9B9794"),
    STEPPE(22, "#727543"),
    MARSH(23, "#2B6548"),
    TAR(24, "#121528"),
    DOOR_WOOD(25, "#293A02"),
    DOOR_ROCK(26, "#726E6B"),
    DOOR_GOLD(27, "#1A3418"),
    DOOR_SILVER(28, "#362720"),
    DOOR_STEEL(29, "#2B6548"),
    SNOW(30, "#FFFFFF"),
    BUSH(31, "#293A02"),
    KELP(32, "#366503"),
    REED(33, "#366503"),
    ENCHANTED_BUSH(34, "#1A3418"),
    INFECTED_BUSH(35, "#DD0229"),
    SLATE_SLABS(36, "#636363"),
    MARBLE_SLABS(37, "#636363"),
    LAWN(38, "#366503"),
    WOODEN_PLANKS_TAR(39, "#726650"),
    MYCELIUM_LAWN(40, "#470233"),
    ROUGH_COBBLESTONE(41, "#5C5349"),
    ROUND_COBBLESTONE(42, "#5C5349"),

    TREE_BIRCH(100, "#293A02"),
    TREE_PINE(101, "#293A02"),
    TREE_OAK(102, "#293A02"),
    TREE_CEDAR(103, "#293A02"),
    TREE_WILLOW(104, "#293A02"),
    TREE_MAPLE(105, "#293A02"),
    TREE_APPLE(106, "#293A02"),
    TREE_LEMON(107, "#293A02"),
    TREE_OLIVE(108, "#293A02"),
    TREE_CHERRY(109, "#293A02"),
    TREE_CHESTNUT(110, "#293A02"),
    TREE_WALNUT(111, "#293A02"),
    TREE_FIR(112, "#293A02"),
    TREE_LINDEN(113, "#293A02"),

    INFECTED_TREE_BIRCH(114, "#3A0229"),
    INFECTED_TREE_PINE(115, "#3A0229"),
    INFECTED_TREE_OAK(116, "#3A0229"),
    INFECTED_TREE_CEDAR(117, "#3A0229"),
    INFECTED_TREE_WILLOW(118, "#3A0229"),
    INFECTED_TREE_MAPLE(119, "#3A0229"),
    INFECTED_TREE_APPLE(120, "#3A0229"),
    INFECTED_TREE_LEMON(121, "#3A0229"),
    INFECTED_TREE_OLIVE(122, "#3A0229"),
    INFECTED_TREE_CHERRY(123, "#3A0229"),
    INFECTED_TREE_CHESTNUT(124, "#3A0229"),
    INFECTED_TREE_WALNUT(125, "#3A0229"),
    INFECTED_TREE_FIR(126, "#3A0229"),
    INFECTED_TREE_LINDEN(127, "#3A0229"),

    ENCHANTED_TREE_BIRCH(128, "#1A3418"),
    ENCHANTED_TREE_PINE(129, "#1A3418"),
    ENCHANTED_TREE_OAK(130, "#1A3418"),
    ENCHANTED_TREE_CEDAR(131, "#1A3418"),
    ENCHANTED_TREE_WILLOW(132, "#1A3418"),
    ENCHANTED_TREE_MAPLE(133, "#1A3418"),
    ENCHANTED_TREE_APPLE(134, "#1A3418"),
    ENCHANTED_TREE_LEMON(135, "#1A3418"),
    ENCHANTED_TREE_OLIVE(136, "#1A3418"),
    ENCHANTED_TREE_CHERRY(137, "#1A3418"),
    ENCHANTED_TREE_CHESTNUT(138, "#1A3418"),
    ENCHANTED_TREE_WALNUT(139, "#1A3418"),
    ENCHANTED_TREE_FIR(140, "#1A3418"),
    ENCHANTED_TREE_LINDEN(141, "#1A3418"),

    BUSH_LAVENDER(142, "#293A02"),
    BUSH_ROSE(143, "#293A02"),
    BUSH_THORN(144, "#293A02"),
    BUSH_GRAPE(145, "#293A02"),
    BUSH_CAMELLIA(146, "#293A02"),
    BUSH_OLEANDER(147, "#293A02"),

    INFECTED_BUSH_LAVENDER(148, "#3A0229"),
    INFECTED_BUSH_ROSE(149, "#3A0229"),
    INFECTED_BUSH_THORN(150, "#3A0229"),
    INFECTED_BUSH_GRAPE(151, "#3A0229"),
    INFECTED_BUSH_CAMELLIA(152, "#3A0229"),
    INFECTED_BUSH_OLEANDER(153, "#3A0229"),

    ENCHANTED_BUSH_LAVENDER(154, "#1A3418"),
    ENCHANTED_BUSH_ROSE(155, "#1A3418"),
    ENCHANTED_BUSH_THORN(156, "#1A3418"),
    ENCHANTED_BUSH_GRAPE(157, "#1A3418"),
    ENCHANTED_BUSH_CAMELLIA(158, "#1A3418"),
    ENCHANTED_BUSH_OLEANDER(159, "#1A3418"),

    CAVE(200, "#838383"),
    CAVE_ENTRANCE(201, "#1C6ADE"),
    CAVE_ROCK(202, "#464646"),
    CAVE_REINFORCED(203, "#00FF00"),
    CAVE_LAVA(204, "#FF977C"),
    CAVE_SLATE(205, "#1E1E1E"),
    CAVE_MARBLE(206, "#20797B"),
    CAVE_REINFORCED_FLOOR(207, "#908EFF"),

    CAVE_VEIN_GOLD(220, "#808000"),
    CAVE_VEIN_SILVER(221, "#F4F4F4"),
    CAVE_VEIN_IRON(222, "#FF8000"),
    CAVE_VEIN_COPPER(223, "#FF0000"),
    CAVE_VEIN_LEAD(224, "#FF00FF"),
    CAVE_VEIN_ZINC(225, "#8000FF"),
    CAVE_VEIN_TIN(226, "#9BFF00"),
    CAVE_VEIN_ADAMANTINE(227, "#8CCBFF"),
    CAVE_VEIN_GLIMMERSTEEL(228, "#FFD333"),

    COBBLESTONE_NW(240, "#5C5349"),
    COBBLESTONE_NE(241, "#5C5349"),
    COBBLESTONE_SE(242, "#5C5349"),
    COBBLESTONE_SW(243, "#5C5349");

    private static final TileType[] types = new TileType[256];
    private byte type;
    private Color color;

    public byte getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    TileType(int type, String color) {
        this.type = (byte) type;
        this.color = Color.decode(color);
    }

    public static TileType get(byte type) {
        TileType tileType = types[type & 0xFF];
        return tileType != null ? tileType : DIRT;
    }

    static {
        for (TileType tileType : values()) {
            types[tileType.getType() & 0xFF] = tileType;
        }
    }

}