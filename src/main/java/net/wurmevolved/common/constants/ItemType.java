package net.wurmevolved.common.constants;

public enum ItemType {

    INVENTORY("Inventory", true, ItemIcon.INVENTORY, ""),
    HATCHET("hatchet", false, ItemIcon.SMALL_AXE, "model.weapon.hatchet");

    ItemType(String name, boolean container, ItemIcon icon, String model) {
        this.name = name;
        this.container = container;
        this.icon = icon;
        this.model = model;
    }

    private String name;
    private boolean container;
    private ItemIcon icon;
    private String model;

    public String getName() {
        return name;
    }

    public boolean isContainer() {
        return container;
    }

    public ItemIcon getIcon() {
        return icon;
    }

    public String getModel() {
        return model;
    }

}
