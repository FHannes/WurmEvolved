package com.wurmemu.server.game.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Objects of this class represent positions in the game world. The coordinates consist out of an (x, y) for the
 * position, z for the height and finally the layer for the game layer on which the position is located. All (x, y)
 * coordinates are represented as tile coordinates, to avoid using two different coordinates systems as the client does.
 * The object coordinates the client uses can be obtained with {@link #getObjectX()} and {@link #getObjectY()}.
 */
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

    public float getObjectX() {
        return x * 4;
    }

    public float getObjectY() {
        return y * 4;
    }

    public short getTileX() {
        return (short) Math.floor(x);
    }

    public short getTileY() {
        return (short) Math.floor(y);
    }

    public short getTileRelX() {
        return (short) (x - getTileX());
    }

    public short getTileRelY() {
        return (short) (y - getTileY());
    }

    public float distance(Position pos) {
        float xDiff = pos.x - x;
        float yDiff = pos.y - y;
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public float maxAxisDistance(Position pos) {
        return Math.max(Math.abs(pos.x - x), Math.abs(pos.y - y));
    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
