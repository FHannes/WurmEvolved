package com.wurmemu.server.game.data;

import com.wurmemu.common.constants.ItemIcon;
import com.wurmemu.server.game.logic.entities.GameEntity;

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

    public abstract String getName();

    public abstract boolean isContainer();

    public abstract ItemIcon getIcon();

    public abstract String getModel();

}
