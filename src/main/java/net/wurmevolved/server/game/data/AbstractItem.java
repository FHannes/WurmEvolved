package net.wurmevolved.server.game.data;

import net.wurmevolved.common.constants.BodyType;
import net.wurmevolved.common.constants.ItemIcon;
import net.wurmevolved.common.constants.Material;
import net.wurmevolved.common.constants.Rarity;
import net.wurmevolved.server.game.logic.GameEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "items")
public abstract class AbstractItem implements GameEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "item_id")
    private AbstractItem parent;

    private Position pos;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    @MapKey(name = "id")
    private Map<Long, AbstractItem> items = new HashMap<>();

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "quality", nullable = false)
    private float quality;

    @Column(name = "damage", nullable = false)
    private float damage;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "material", nullable = false)
    private Material material;

    @Column(name = "temperature", nullable = false)
    private byte temperature;

    @Enumerated
    @Column(name = "rarity", nullable = false)
    private Rarity rarity;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AbstractItem getParent() {
        return parent;
    }

    public void setParent(AbstractItem parent) {
        this.parent = parent;
    }

    @Override
    public Position getPos() {
        return pos;
    }

    @Override
    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Collection<AbstractItem> getItems() {
        return items.values();
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public byte getTemperature() {
        return temperature;
    }

    public void setTemperature(byte temperature) {
        this.temperature = temperature;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public abstract String getItemName();

    public abstract boolean isWound();

    public abstract boolean isBody();

    public abstract boolean isContainer();

    public abstract boolean isNoDrop();

    public abstract ItemIcon getIcon();

    public abstract String getModel();

    public BodyType getBodyType() {
        if (getParent() != null && getParent().getBodyType() != null) {
            return BodyType.LEFT_HELD_ITEM;
        }
        return null;
    }

    public short getFlags() {
        short flags = 0;
        if (isWound()) {
            flags |= 2;
        }
        if (isBody()) {
            flags |= 4;
        }
        if (isContainer()) {
            flags |= 8;
        }
        if (isNoDrop()) {
            flags |= 16;
        }
        return flags;
    }

    public void addItem(AbstractItem item) {
        items.put(item.getId(), item);
        item.setParent(this);
    }

    public float calculateWeight() {
        float weight = getWeight();
        for (AbstractItem item : items.values()) {
            weight += item.calculateWeight();
        }
        return weight;
    }

}
