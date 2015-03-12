package com.wurmemu.server.game.data;

import com.wurmemu.common.constants.ItemIcon;
import com.wurmemu.common.constants.ItemType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("T")
@Table(name = "typed_items")
public class Item extends AbstractItem {

    @Column(name = "type", nullable = false)
    private ItemType type;

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public boolean isContainer() {
        return type.isContainer();
    }

    @Override
    public ItemIcon getIcon() {
        return type.getIcon();
    }

    @Override
    public String getModel() {
        return type.getModel();
    }

}
