package com.wurmemu.server.game.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Position {

    @Column(name = "x", nullable = false)
    private float x;

    @Column(name = "y", nullable = false)
    private float y;

    @Column(name = "z", nullable = false)
    private float z;

    @Column(name = "layer", nullable = false)
    private byte layer;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public byte getLayer() {
        return layer;
    }

    public void setLayer(byte layer) {
        this.layer = layer;
    }

    public float getClientX() {
        return x * 4;
    }

    public float getClientY() {
        return y * 4;
    }

    public short getTileX() {
        return (short) Math.floor(x);
    }

    public short getTileY() {
        return (short) Math.floor(y);
    }

    public short getTileRelativeX() {
        return (short) (x - getTileX());
    }

    public short getTileRelativeY() {
        return (short) (y - getTileY());
    }

    public float distance(Position pos) {
        float xDiff = pos.x - x;
        float yDiff = pos.y - y;
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public float maxAxisDistance(Position pos) {
        return (float) Math.max(Math.abs(pos.x - x), Math.abs(pos.y - y));
    }

}
