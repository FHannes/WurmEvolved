package com.wurmemu.server.game.data;

import com.wurmemu.common.constants.TileType;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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

}