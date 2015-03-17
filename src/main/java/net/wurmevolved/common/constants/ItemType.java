package net.wurmevolved.common.constants;

public enum ItemType {

    INVENTORY("inventory", true, ItemIcon.INVENTORY, "", Material.UNKNOWN, 0F),
    HATCHET("hatchet", false, ItemIcon.SMALL_AXE, "model.weapon.hatchet", Material.BIRCHWOOD, 1.5F),
    BODY("body", false, true, true, false, ItemIcon.BODY, "", Material.FLESH, 0F),
    HEAD("head", false, true, true, false, ItemIcon.HEAD, "", Material.FLESH, 0F),
    NECK("neck", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    FACE("face", false, true, true, false, ItemIcon.HEAD, "", Material.FLESH, 0F),
    TORSO("torso", false, true, true, false, ItemIcon.TORSO, "", Material.FLESH, 0F),
    CAPE("cape", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    LEFT_SHOULDER("left shoulder", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    RIGHT_SHOULDER("right shoulder", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    BACK("back", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    TABARD("tabard", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    LEFT_ARM("left arm", false, true, true, false, ItemIcon.ARM, "", Material.FLESH, 0F),
    SHIELD_SLOT("shield slot", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    LEFT_HAND("left hand", false, true, true, false, ItemIcon.HAND, "", Material.FLESH, 0F),
    LEFT_RING("left ring", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    LEFT_HELD_ITEM("left held item", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    RIGHT_ARM("right arm", false, true, true, false, ItemIcon.ARM, "", Material.FLESH, 0F),
    RIGHT_HAND("right hand", false, true, true, false, ItemIcon.HAND, "", Material.FLESH, 0F),
    RIGHT_RING("right ring", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    RIGHT_HELD_ITEM("right held item", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    LEGS("legs", false, true, true, false, ItemIcon.LEGS, "", Material.FLESH, 0F),
    BELT("belt", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    HIP_SLOT("hip slot", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F),
    LEFT_FOOT("left foot", false, true, true, false, ItemIcon.FOOT, "", Material.FLESH, 0F),
    RIGHT_FOOT("right foot", false, true, true, false, ItemIcon.FOOT, "", Material.FLESH, 0F);

    ItemType(String name, boolean container, ItemIcon icon, String model, Material material, float weight) {
        this(name, false, false, container, false, icon, model, material, weight);
    }

    ItemType(String name, boolean wound, boolean body, boolean container, boolean noDrop, ItemIcon icon, String model, Material material, float weight) {
        this.name = name;
        this.wound = wound;
        this.body = body;
        this.container = container;
        this.noDrop = noDrop;
        this.icon = icon;
        this.model = model;
        this.material = material;
        this.weight = weight;
    }

    private String name;
    private boolean wound;
    private boolean body;
    private boolean container;
    private boolean noDrop;
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

    public boolean isNoDrop() {
        return noDrop;
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
