package net.wurmevolved.server.game.data;

import net.wurmevolved.common.constants.ItemIcon;
import net.wurmevolved.server.game.logic.GameEntity;

import javax.persistence.*;
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "container_items",
            joinColumns = {@JoinColumn(name="container_id", referencedColumnName="item_id")},
            inverseJoinColumns = {@JoinColumn(name="item_id", referencedColumnName="item_id", unique = true)}
    )
    @MapKey(name = "id")
    private Map<Long, AbstractItem> items = new HashMap<>();

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "quality", nullable = false)
    private float quality;

    @Column(name = "damage", nullable = false)
    private float damage;

    @Override
    public long getId() {
        return id;
    }

    @Override
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

    public abstract String getName();

    public abstract boolean isContainer();

    public abstract ItemIcon getIcon();

    public abstract String getModel();

}
