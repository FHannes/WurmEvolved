package com.wurmemu.server.game.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "players")
class Player {

    @Id
    @GeneratedValue
    @Column(name = "player_id", nullable = false)
    long id

    @Column(name = "username", nullable = false)
    String username

}
