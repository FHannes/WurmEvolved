package net.wurmevolved.server.game.net.packets.server;

import io.netty.buffer.ByteBuf;
import net.wurmevolved.common.constants.ItemIcon;
import net.wurmevolved.common.constants.Material;
import net.wurmevolved.common.constants.Rarity;
import net.wurmevolved.common.protocol.Protocol;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.Packet;

import java.awt.*;

@Packet(Protocol.PACKET_ADD_ITEM)
public class AddItemPacket extends AbstractPacket {

    private long containerID;
    private long parentID;
    private long itemID;
    private ItemIcon icon;
    private String itemName;
    private String name;
    private float quality;
    private float damage;
    private float weight;
    private Color color;
    private int price;
    private ItemIcon impIcon;
    private short flags;
    private Material material;
    private byte temperature;
    private Rarity rarity;

    public AddItemPacket(long containerID, long parentID, long itemID, ItemIcon icon, String itemName, String name, float quality, float damage, float weight, Color color, int price, ItemIcon impIcon, short flags, Material material, byte temperature, Rarity rarity) {
        this.containerID = containerID;
        this.parentID = parentID;
        this.itemID = itemID;
        this.icon = icon;
        this.itemName = itemName;
        this.name = name;
        this.quality = quality;
        this.damage = damage;
        this.weight = weight;
        this.color = color;
        this.price = price;
        this.impIcon = impIcon;
        this.flags = flags;
        this.material = material;
        this.temperature = temperature;
        this.rarity = rarity;
    }

    @Override
    public void encode(ByteBuf out) {
        out.writeLong(getContainerID());
        out.writeLong(getParentID());
        out.writeLong(getItemID());
        out.writeShort(getIcon().getValue());
        writeString(out, getItemName());
        writeString(out, getName());
        out.writeFloat(getQuality());
        out.writeFloat(getDamage());
        out.writeInt(Math.round(getWeight() * 1000F));
        out.writeBoolean(getColor() != null);
        if (getColor() != null) {
            out.writeByte((byte) getColor().getRed());
            out.writeByte((byte) getColor().getGreen());
            out.writeByte((byte) getColor().getBlue());
        }
        out.writeBoolean(getPrice() != -1);
        if (getPrice() != -1) {
            out.writeInt(getPrice());
        }
        out.writeBoolean(getImpIcon() != null);
        if (getImpIcon() != null) {
            out.writeShort(getImpIcon().getValue());
        }
        out.writeShort(getFlags());
        out.writeByte(getMaterial().getId());
        out.writeByte(getTemperature());
        out.writeByte(getRarity().ordinal());
        // Unknown optional byte value
    }

    public long getContainerID() {
        return containerID;
    }

    public void setContainerID(long containerID) {
        this.containerID = containerID;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public ItemIcon getIcon() {
        return icon;
    }

    public void setIcon(ItemIcon icon) {
        this.icon = icon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemIcon getImpIcon() {
        return impIcon;
    }

    public void setImpIcon(ItemIcon impIcon) {
        this.impIcon = impIcon;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
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

}
