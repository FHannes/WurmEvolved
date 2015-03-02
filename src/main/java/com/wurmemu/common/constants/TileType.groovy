package com.wurmemu.common.constants

import java.awt.*

enum TileType {

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
    COBBLESTONE_SW(243, "#5C5349")

    private static types = new TileType[256]
    byte type
    Color color

    TileType(int type, String color) {
        this.type = (byte) type
        this.color = Color.decode(color)
    }

    static TileType get(byte type) {
        types[((int) type + 256) % 256] ?: DIRT
    }

    static {
        for (TileType tileType : values()) {
            types[((int) tileType.getType() + 256) % 256] = tileType
        }
    }

}