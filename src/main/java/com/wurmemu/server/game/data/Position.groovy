package com.wurmemu.server.game.data

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Position {

    @Column(name = "x", nullable = false)
    float x

    @Column(name = "y", nullable = false)
    float y

    @Column(name = "z", nullable = false)
    float z

    @Column(name = "layer", nullable = false)
    byte layer

    float getClientX() {
        x * 4
    }

    float getClientY() {
        y * 4
    }

    short getTileX() {
        (short) Math.floor(x)
    }

    short getTileY() {
        (short) Math.floor(y)
    }

    short getTileRelativeX() {
        x - getTileX()
    }

    short getTileRelativeY() {
        y - getTileY()
    }

}
