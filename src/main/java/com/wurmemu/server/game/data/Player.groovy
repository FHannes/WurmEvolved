package com.wurmemu.server.game.data

import com.wurmemu.server.game.logic.entities.GameEntity

import javax.persistence.*

@Entity
@Table(name = "players")
class Player implements GameEntity {

    @Id
    @Column(name = "player_id", nullable = false)
    long id

    @Column(name = "username", nullable = false)
    String username

    Position pos

}
