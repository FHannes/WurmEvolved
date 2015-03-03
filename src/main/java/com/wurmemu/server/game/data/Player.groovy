package com.wurmemu.server.game.data

import javax.persistence.*

@Entity
@Table(name = "players")
class Player {

    @Id
    @Column(name = "player_id", nullable = false)
    long id

    @Column(name = "username", nullable = false)
    String username

    @Column(name = "x", nullable = false)
    float x = 0

    @Column(name = "y", nullable = false)
    float y = 0

    @Column(name = "z", nullable = false)
    float z = 0

    @Column(name = "layer", nullable = false)
    byte layer = 0

}
