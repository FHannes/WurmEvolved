package net.wurmevolved.common.constants;

public enum ItemIcon {

    BODY(0),
    TORSO(1),
    HEAD(2),
    ARM(3),
    HAND(4),
    LEGS(5),
    FOOT(6),
    INVENTORY(20),
    UNKNOWN(60),
    SMALL_AXE(1207);

    private static final ItemIcon[] icons = new ItemIcon[65535];
    private short value;

    ItemIcon(int value) {
        this.value = (short) value;
    }

    public short getValue() {
        return value;
    }

    public static ItemIcon get(short value) {
        ItemIcon itemIcon = icons[value & 0xFFFF];
        return itemIcon != null ? itemIcon : UNKNOWN;
    }

    static {
        for (ItemIcon itemIcon : values()) {
            icons[itemIcon.getValue() & 0xFFFF] = itemIcon;
        }
    }

}
