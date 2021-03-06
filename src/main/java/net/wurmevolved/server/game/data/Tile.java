package net.wurmevolved.server.game.data;

import net.wurmevolved.common.constants.TileType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tiles")
public class Tile {

    @EmbeddedId
    private TilePos pos;

    @Column(name = "type", nullable = false)
    private TileType type;

    @Column(name = "subtype", nullable = false)
    private byte subtype;

    @Column(name = "age", nullable = false)
    private byte age;

    @Column(name = "height", nullable = false)
    private short height;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<AbstractItem> items = new HashSet<>();

    public Tile() {

    }

    public Tile(TilePos pos, TileType type, byte subtype, byte age, short height) {
        this.pos = pos;
        this.type = type;
        this.subtype = subtype;
        this.age = age;
        this.height = height;
    }

    public TilePos getPos() {
        return pos;
    }

    public void setPos(TilePos pos) {
        this.pos = pos;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public byte getSubtype() {
        return subtype;
    }

    public void setSubtype(byte subtype) {
        this.subtype = subtype;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public Set<AbstractItem> getItems() {
        return items;
    }

    public void addItem(AbstractItem item) {
        items.add(item);
    }

}