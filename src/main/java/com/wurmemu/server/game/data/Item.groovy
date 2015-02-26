package com.wurmemu.server.game.data

import javax.persistence.*

@Entity
@Table(name = "items")
class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id", nullable = false)
    long id

}
