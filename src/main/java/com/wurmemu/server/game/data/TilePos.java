package com.wurmemu.server.game.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TilePos implements Serializable {

    @Column(name = "x", nullable = false)
    private short x;

    @Column(name = "y", nullable = false)
    private short y;

    public TilePos() {

    }

    public TilePos(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public short getX() {
        return x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(short y) {
        this.y = y;
    }

}
