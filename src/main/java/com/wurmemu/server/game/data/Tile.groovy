package com.wurmemu.server.game.data

import com.wurmemu.common.constants.TileType

import javax.persistence.*

@Entity
@Table(name = "tiles")
class Tile {

    @EmbeddedId
    TilePos pos

    @Column(name = "type", nullable = false)
    TileType type

    @Column(name = "subtype", nullable = false)
    byte subtype

    @Column(name = "age", nullable = false)
    byte age

    @Column(name = "height", nullable = false)
    short height

}

@Embeddable
class TilePos implements Serializable {

    @Column(name = "x", nullable = false)
    int x

    @Column(name = "y", nullable = false)
    int y

}