package net.wurmevolved.server.game.data;

import net.wurmevolved.common.constants.BodyType;
import net.wurmevolved.common.constants.ItemIcon;
import net.wurmevolved.common.constants.ItemType;

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
    public String getItemName() {
        return type.getName();
    }

    @Override
    public boolean isWound() {
        return type.isWound();
    }

    @Override
    public boolean isBody() {
        return type.isBody();
    }

    @Override
    public boolean isContainer() {
        return type.isContainer();
    }

    @Override
    public boolean isNoDrop() {
        return type.isNoDrop();
    }

    @Override
    public ItemIcon getIcon() {
        return type.getIcon();
    }

    @Override
    public String getModel() {
        return type.getModel();
    }

    @Override
    public BodyType getBodyType() {
        switch (getType()) {
            case BODY:
                return BodyType.BODY;
            case HEAD:
                return BodyType.HEAD;
            case NECK:
                return BodyType.NECK;
            case FACE:
                return BodyType.FACE;
            case TORSO:
                return BodyType.TORSO;
            case CAPE:
                return BodyType.CAPE;
            case LEFT_SHOULDER:
                return BodyType.LEFT_SHOULDER;
            case RIGHT_SHOULDER:
                return BodyType.RIGHT_SHOULDER;
            case BACK:
                return BodyType.BACK;
            case TABARD:
                return BodyType.TABARD;
            case LEFT_ARM:
                return BodyType.LEFT_ARM;
            case SHIELD_SLOT:
                return BodyType.SHIELD_SLOT;
            case LEFT_HAND:
                return BodyType.LEFT_HAND;
            case LEFT_RING:
                return BodyType.LEFT_RING;
            case LEFT_HELD_ITEM:
                return BodyType.LEFT_HELD_ITEM;
            case RIGHT_ARM:
                return BodyType.RIGHT_ARM;
            case RIGHT_HAND:
                return BodyType.RIGHT_HAND;
            case RIGHT_RING:
                return BodyType.RIGHT_RING;
            case RIGHT_HELD_ITEM:
                return BodyType.RIGHT_HELD_ITEM;
            case LEGS:
                return BodyType.LEGS;
            case BELT:
                return BodyType.BELT;
            case HIP_SLOT:
                return BodyType.HIP_SLOT;
            case LEFT_FOOT:
                return BodyType.LEFT_FOOT;
            case RIGHT_FOOT:
                return BodyType.RIGHT_FOOT;
            default:
                return super.getBodyType();
        }
    }

}
