package com.wurmemu.server.game.data

import javax.persistence.*

@Entity
@Table(name = "items")
class Item {

    @Id
    @Column(name = "item_id", nullable = false)
    long id

    Position position

}
