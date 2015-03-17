package net.wurmevolved.common.constants;

public enum ItemType {

    INVENTORY("Inventory", true, ItemIcon.INVENTORY, "", Material.UNKNOWN, 0F),
    HATCHET("hatchet", false, ItemIcon.SMALL_AXE, "model.weapon.hatchet", Material.BIRCHWOOD, 1.5F);

    ItemType(String name, boolean container, ItemIcon icon, String model, Material material, float weight) {
        this(name, false, false, container, icon, model, material, weight);
    }

    ItemType(String name, boolean wound, boolean body, boolean container, ItemIcon icon, String model, Material material, float weight) {
        this.name = name;
        this.wound = wound;
        this.body = body;
        this.container = container;
        this.icon = icon;
        this.model = model;
        this.material = material;
        this.weight = weight;
    }

    private String name;
    private boolean wound;
    private boolean body;
    private boolean container;
    private ItemIcon icon;
    private String model;
    private Material material;
    private float weight;

    public String getName() {
        return name;
    }

    public boolean isWound() {
        return wound;
    }

    public boolean isBody() {
        return body;
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

    public Material getMaterial() {
        return material;
    }

    public float getWeight() {
        return weight;
    }

}
