package net.wurmevolved.common.constants;

public enum ItemType {

    INVENTORY("inventory", true, ItemIcon.INVENTORY, "", Material.UNKNOWN, 0F,
            "This is where you keep your things."),
    HATCHET("hatchet", false, ItemIcon.SMALL_AXE, "model.weapon.hatchet", Material.BIRCHWOOD, 1.5F,
            "A short but sturdy axe with a thick blade specially designed to cut down trees with but poor in combat."),
    BODY("body", false, true, true, false, ItemIcon.BODY, "", Material.FLESH, 0F,
            "A body."),
    HEAD("head", false, true, true, false, ItemIcon.HEAD, "", Material.FLESH, 0F,
            "A head."),
    NECK("neck", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    FACE("face", false, true, true, false, ItemIcon.HEAD, "", Material.FLESH, 0F,
            "A face."),
    TORSO("torso", false, true, true, false, ItemIcon.TORSO, "", Material.FLESH, 0F,
            "A body without the extremities."),
    CAPE("cape", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    LEFT_SHOULDER("left shoulder", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    RIGHT_SHOULDER("right shoulder", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    BACK("back", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    TABARD("tabard", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    LEFT_ARM("left arm", false, true, true, false, ItemIcon.ARM, "", Material.FLESH, 0F,
            "An arm."),
    SHIELD_SLOT("shield slot", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    LEFT_HAND("left hand", false, true, true, false, ItemIcon.HAND, "", Material.FLESH, 0F,
            "A hand."),
    LEFT_RING("left ring", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    LEFT_HELD_ITEM("left held item", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    RIGHT_ARM("right arm", false, true, true, false, ItemIcon.ARM, "", Material.FLESH, 0F,
            "An arm."),
    RIGHT_HAND("right hand", false, true, true, false, ItemIcon.HAND, "", Material.FLESH, 0F,
            "A hand."),
    RIGHT_RING("right ring", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    RIGHT_HELD_ITEM("right held item", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    LEGS("legs", false, true, true, false, ItemIcon.LEGS, "", Material.FLESH, 0F,
            "Two legs."),
    BELT("belt", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    HIP_SLOT("hip slot", false, true, true, false, ItemIcon.UNKNOWN, "", Material.FLESH, 0F,
            "A place to put equipment."),
    LEFT_FOOT("left foot", false, true, true, false, ItemIcon.FOOT, "", Material.FLESH, 0F,
            "A foot."),
    RIGHT_FOOT("right foot", false, true, true, false, ItemIcon.FOOT, "", Material.FLESH, 0F,
            "A foot.");

    ItemType(String name, boolean container, ItemIcon icon, String model, Material material, float weight, String examine) {
        this(name, false, false, container, false, icon, model, material, weight, examine);
    }

    ItemType(String name, boolean wound, boolean body, boolean container, boolean noDrop, ItemIcon icon, String model, Material material, float weight, String examine) {
        this.name = name;
        this.wound = wound;
        this.body = body;
        this.container = container;
        this.noDrop = noDrop;
        this.icon = icon;
        this.model = model;
        this.material = material;
        this.weight = weight;
        this.examine = examine;
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
    private String examine;

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

    public String getExamine() {
        return examine;
    }

}
