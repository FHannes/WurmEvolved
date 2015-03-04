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

    Position pos

}
