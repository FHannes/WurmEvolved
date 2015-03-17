package net.wurmevolved.common.constants;

public enum Material {

    UNKNOWN(0),
    FLESH(1),
    RYE(3),
    OAT(4),
    BARLEY(5),
    WHEAT(6),
    GOLD(7),
    SILVER(8),
    STEEL(9),
    COPPER(10),
    IRON(11),
    LEAD(12),
    ZINC(13),
    BIRCHWOOD(14),
    STONE(15),
    LEATHER(16),
    COTTON(17),
    CLAY(18),
    POTTERY(19),
    GLASS(20),
    MAGIC(21),
    VEGETARIAN(22),
    FIRE(23),
    OIL(25),
    WATER(26),
    CHARCOAL(27),
    DAIRY(28),
    HONEY(29),
    BRASS(30),
    BRONZE(31),
    FAT(32),
    PAPER(23),
    TIN(34),
    BONE(35),
    SALT(36),
    PINEWOOD(37),
    OEAKWOOD(38),
    CEDARWOOD(39),
    WILLOW(40),
    MAPLEWOOD(41),
    APPLEWOOD(42),
    LEMONWOOD(43),
    OLIVEWOOD(44),
    CHERRYWOOD(45),
    LAVENDERWOOD(46),
    ROSEWOOD(47),
    THORN(48),
    GRAPEWOOD(49),
    CAMELLIAWOOD(50),
    OLEANDERWOOD(51),
    CRYSTAL(52),
    DIAMOND(54),
    ADAMANTINE(56),
    GLIMMERSTEEL(57),
    TAR(58),
    PEAT(59),
    REED(60),
    SLATE(61),
    MARBLE(62),
    CHESTNUT(63),
    WALNUT(64),
    FIRWOOD(65),
    LINDENWOOD(66),
    SERYLL(67),
    IVY(68),
    WOOL(69),
    STRAW(70);

    private static final Material[] materials = new Material[256];
    private byte id;

    Material(int id) {
        this.id = (byte) id;
    }

    public short getId() {
        return id;
    }

    public static Material get(short id) {
        Material material = materials[id & 0xFF];
        return material != null ? material : UNKNOWN;
    }

    static {
        for (Material material : values()) {
            materials[material.getId() & 0xFF] = material;
        }
    }

}
